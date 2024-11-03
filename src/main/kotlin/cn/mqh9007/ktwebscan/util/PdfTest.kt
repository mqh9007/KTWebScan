import cn.mqh9007.ktwebscan.pojo.Users
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.springframework.stereotype.Component

@Component
class PdfTest {
    fun test(args: Array<String>) {
        val doc = PDDocument()

        val pdPage = PDPage()

        doc.addPage(pdPage)

        val contentStream = PDPageContentStream(doc, pdPage)

        contentStream.beginText()

        val inputStream = Users::class.java.classLoader.getResourceAsStream("Roboto-Thin.ttf")

        contentStream.setFont(PDType0Font.load(doc, inputStream,), 30F)

        contentStream.showText("zhangsan")

        contentStream.endText()

        doc.save("test.pdf")

        doc.close()

    }
}