package br.com.deveficiente.nossomercadolivreapi.shared;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

public class Markdown {

    private static List<Extension> extensions = Arrays.asList(AutolinkExtension.create());
    private static Parser parser = Parser.builder().extensions(extensions).build();
    private static HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();

    public static String toHtml(String text) {
        Node document = parser.parse(text);
        return renderer.render(document);
    }
}
