package com.hellcaster.blogging.config;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String from, String to, String subject, String body) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setSubject(subject);
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setText(body, body);
            }
        };
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom(from);
//        simpleMailMessage.setTo(to);
//        simpleMailMessage.setSubject(subject);
//        simpleMailMessage.setText(body);
        try{
            javaMailSender.send(preparator);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public void sendMailWithAttachment(String from, String to, String subject, String body, List<MultipartFile> listMultipartFile) {
        List<File> listFile = new ArrayList<File>();
        try{
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    mimeMessage.setFrom(new InternetAddress(from));
                    mimeMessage.setSubject(subject);
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setText(body, body);

                    for(MultipartFile multipartFile : listMultipartFile){
                        File file = convertMultiPartToFile(multipartFile);
                        helper.addAttachment(file.getName(), file);
                        listFile.add(file);
                    }
                }
            };
            javaMailSender.send(preparator);
            for(File file : listFile){
                file.delete();
                file.exists();
            }
            listFile.clear();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.gc();
        }
    }

    public void sendMailWithInlineResources(String from, String to, String subject, String body, List<MultipartFile> listMultipartFile) {
        List<File> listFile = new ArrayList<File>();
        try{
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    mimeMessage.setFrom(new InternetAddress(from));
                    mimeMessage.setSubject(subject);
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setText(body, body);

                    for(MultipartFile multipartFile : listMultipartFile){
                        File file = convertMultiPartToFile(multipartFile);
                        helper.addInline(file.getName(), file);
                        listFile.add(file);
                    }
                }
            };
            javaMailSender.send(preparator);
            for(File file : listFile){
                file.delete();
                file.exists();
            }
            listFile.clear();
        }catch (MailException e){
            e.printStackTrace();
        }finally {
            System.gc();
        }
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        convFile.exists();
        return convFile;
    }
}
