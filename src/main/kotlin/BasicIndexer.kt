import org.apache.lucene.analysis.ja.JapaneseAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.TextField
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.FSDirectory
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

/**
 * 基本インデクサクラス
 */
data class BasicIndexer(
    private val indexDirectoryPath: String
) {
    private var indexWriter: IndexWriter
    init {
        val directory = FSDirectory.open(Paths.get(indexDirectoryPath))
        this.indexWriter = IndexWriter(directory, IndexWriterConfig(JapaneseAnalyzer()))
    }

    /**
     * ライターを閉じる
     */
    fun close() = indexWriter.close()

    private fun getDocument(file: File): Document {
        val contextField = TextField("contents", FileReader(file))
        val fileNameField = TextField("filename", file.name, Field.Store.YES)
        val filePathField = TextField("filepath", file.canonicalPath, Field.Store.YES)

        Document().let {
            it.add(contextField)
            it.add(fileNameField)
            it.add(filePathField)
            return it
        }
    }

    /**
     * ディレクトリにあるファイルからインデックスを生成する
     */
    fun createIndex(sourcePath: String): Int {
        val files = File(sourcePath).listFiles()
        if (files == null) {
            return 0
        }

        files.filter { !it.isDirectory && it.exists() && it.canRead() }
            .forEach {
                println("対象のファイル: ${it.name}")
                this.indexWriter.addDocument(getDocument(it))
            }

        return this.indexWriter.numRamDocs()
    }
}
