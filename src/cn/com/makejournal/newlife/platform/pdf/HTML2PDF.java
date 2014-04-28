package cn.com.makejournal.newlife.platform.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.newlife.pdf.tool.SourceType;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

/**
 * itext5+
 * 
 * @author ray
 * 
 *         将html文件或者是带css的html的文件转换成pdf
 * 
 * */
public class HTML2PDF {

	// private String html_source;
	private String save_target;
	private String images_root;

	public HTML2PDF(String save_target, String images_root) {
		this.save_target = save_target;
		this.images_root = images_root;
	}

	public void parseHtml2Pdf(String target, SourceType type) throws Exception {
		InputStream input = getInputStream(target, type);
		parseHtml2Pdf(input, null);
	}

	public void parseHtml2Pdf(String target, String css_file, SourceType type)
			throws Exception {
		InputStream input = getInputStream(target, type);
		parseHtml2Pdf(input, css_file);
	}

	private void parseHtml2Pdf(InputStream input, String css_file)
			throws Exception {
		if (input == null)
			throw new Exception("源文件不存在");
		Document document = new Document();
		PdfWriter pdfWriter = PdfWriter.getInstance(document,
				new FileOutputStream(save_target));
		document.open();
		XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
		// 字体设置
		XMLWorkerFontProvider fontProvider = getFont();
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		// 设置html解析的标签和样式
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		// 默认是html Tags，如果要解释其他的tags 需要实现自己的Pipeline
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

		if (images_root != null && !"".equals(images_root)) {
			System.out.println(images_root);
			htmlContext.setImageProvider(new AbstractImageProvider() {
				@Override
				public String getImageRootPath() {
					return images_root;
				}
			});
		}

		PdfWriterPipeline pdf = new PdfWriterPipeline(document, pdfWriter);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		if (css_file != null && !"".equals(css_file)
				&& new File(css_file).exists()) {
			parseHtmlWithCSS(helper, html, input, css_file);
		} else {
			helper.parseXHtml(pdfWriter, document,input);
		}
		document.close();
	}

	// 可以设置成想要的字体，这里比较简单
	// windows的字体路径在C:\Windows\Fonts下
	// linux 不清楚
	private XMLWorkerFontProvider getFont() {
		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
		String osname = System.getProperty("os.name");
		if (osname.startsWith("Windows")) {
			System.out.println("System oprating is windows");
			// fontProvider.register("C:/windows/fonts/GARA.TTF");
			// fontProvider.register("C:/windows/fonts/GARAIT.TTF");
			// fontProvider.register("C:/windows/fonts/GARABD.TTF");
			fontProvider.registerDirectory("C:\\windows\\fonts");
			// fontProvider.addFontSubstitute("Gill Sans", "Gill Sans 超粗体");
		}
		return fontProvider;
	}

	private void parseHtmlWithCSS(XMLWorkerHelper helper, HtmlPipeline html,
			InputStream input, String cssfile_source) throws Exception {
		CSSResolver cssResolver = new StyleAttrCSSResolver();
		CssFile cssFile = helper.getCSS(new FileInputStream(cssfile_source));
		cssResolver.addCss(cssFile);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(input);
	}

	private InputStream getResponse(String url) throws Exception {
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		return conn.getInputStream();
	}

	private InputStream getInputStream(String target, SourceType type)
			throws Exception {
		InputStream input = null;
		if (SourceType.URI.equals(type))
			input = getResponse(target);
		if (SourceType.HTML.equals(type))
			input = new FileInputStream(target);
		return input;
	}

	public String getSave_target() {
		return save_target;
	}

	public void setSave_target(String save_target) {
		this.save_target = save_target;
	}

	public static void main(String[] args) {
		try {
			HTML2PDF html2pdf = new HTML2PDF("D:\\test2.pdf", null);
			//html2pdf.parseHtml2Pdf("D:\\html1.html", SourceType.HTML);
			html2pdf.parseHtml2Pdf("http://localhost:8080/html1.html", SourceType.URI);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
