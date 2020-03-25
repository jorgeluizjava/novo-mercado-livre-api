package br.com.deveficiente.nossomercadolivreapi.shared.infra;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Uploader {

    public List<String> upload(List<MultipartFile> multipartFiles);
}
