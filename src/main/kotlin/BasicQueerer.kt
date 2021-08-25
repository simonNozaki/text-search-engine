import org.apache.lucene.analysis.ja.JapaneseAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.ScoreDoc
import org.apache.lucene.search.TopDocs
import org.apache.lucene.store.FSDirectory
import java.nio.file.Paths

/**
 * クエリ実行クラス
 */
data class BasicQueerer(
    private val indexDirectoryPath: String
) {
    private var indexSearcher: IndexSearcher
    private var queryParser: QueryParser

    init {
        val directory = FSDirectory.open(Paths.get(indexDirectoryPath))
        this.indexSearcher = IndexSearcher(DirectoryReader.open(directory))

        this.queryParser = QueryParser("contents", JapaneseAnalyzer())
    }

    fun query(queryString: String): TopDocs {
        return indexSearcher.search(queryParser.parse(queryString), 10)
    }

    fun getDocument(scoreDoc: ScoreDoc): Document {
        return indexSearcher.doc(scoreDoc.doc)
    }
}
