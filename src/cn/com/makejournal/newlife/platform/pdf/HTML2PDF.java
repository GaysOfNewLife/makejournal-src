package org.newlife.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.util.OS;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
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
 *         将html文件或者是带css的html的文件转换成pdf 生成的pdf将会自动分页
 * 
 * */
public class HTML2PDF {

	private String save_target;

	public HTML2PDF(String save_target) {
		this.save_target = save_target;
	}

	public void parseHtml2Pdf(String target, SourceType type) throws Exception {
		InputStream input = getInputStream(target, type);
		parseHtml2Pdf(input, null, null);
	}

	public void parseHtml2Pdf(String target, String css_file,
			String images_root, SourceType type) throws Exception {
		InputStream input = getInputStream(target, type);
		parseHtml2Pdf(input, css_file, images_root);
	}

	private void parseHtml2Pdf(InputStream input, String css_file,
			String images_root) throws Exception {
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
			final String imageRoot = images_root;
			htmlContext.setImageProvider(new AbstractImageProvider() {
				@Override
				public String getImageRootPath() {
					return imageRoot;
				}
			});
		}
		CSSResolver cssResolver = null;
		if (css_file != null && !"".equals(css_file)
				&& new File(css_file).exists()) {
			cssResolver = new StyleAttrCSSResolver();
			CssFile cssFile = helper.getCSS(new FileInputStream(css_file));
			cssResolver.addCss(cssFile);
		} else {
			cssResolver = helper.getDefaultCssResolver(true);
		}
		Pipeline pipeline = new CssResolverPipeline(cssResolver,
				new HtmlPipeline(htmlContext, new PdfWriterPipeline(document,
						pdfWriter)));
		XMLWorker worker = new XMLWorker(pipeline, true);
		XMLParser p = new XMLParser(worker);
		p.parse(input);
		p.flush();
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

	private static String wkhtmltopdfPath = new File("wkhtmltopdf")
			.getAbsolutePath();

	public static void wkhtmltopdf(String url, String saveFile)
			throws Exception {
		if (url == null || "".equals(url) || saveFile == null
				|| "".equals(saveFile))
			return;
		List<String> cmd = new ArrayList<String>();
		if (OS.isWindowsXP()) {
			cmd.add(wkhtmltopdfPath + "\\wkhtmltopdf");
		} else if (OS.isLinux()) {
			cmd.add("wkhtmltopdf");
		} else {
			cmd.add(wkhtmltopdfPath + "\\wkhtmltopdf");
		}
		cmd.add("");
		cmd.add(url);
		cmd.add(saveFile);
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(cmd);
		pb.redirectErrorStream(true);
		pb.start();
	}

	public static void main(String[] args) {
		try {
			// HTML2PDF html2pdf = new HTML2PDF("D:\\test3.pdf");
			// html2pdf.parseHtml2Pdf(
			// "http://localhost:8080/newLife_web_demo/test", null, "D:/",
			// SourceType.URI);
			wkhtmltopdf("http://localhost:8080/newLife_web_demo/test", "D:\\3.pdf");
			// html2pdf.parseHtml2Pdf("D:/html1.html", "D:/style.css", null,
			// SourceType.HTML);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
