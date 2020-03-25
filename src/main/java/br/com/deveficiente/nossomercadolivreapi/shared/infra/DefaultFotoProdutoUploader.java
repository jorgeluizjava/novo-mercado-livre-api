package br.com.deveficiente.nossomercadolivreapi.shared.infra;

import br.com.deveficiente.nossomercadolivreapi.produto.Foto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultFotoProdutoUploader implements Uploader {

    @Override
    public List<String> upload(List<MultipartFile> multipartFiles) {

        List<String> urls = new ArrayList<>();

        for (MultipartFile multipartFile: multipartFiles) {
            System.out.println("Efetuando upload do arquivo " + multipartFile.getOriginalFilename());
            String url = "http://s3.meubucket.com/produtos/fotos/" + multipartFile.getOriginalFilename();
            urls.add(url);
        }


        return urls;
    }
}
