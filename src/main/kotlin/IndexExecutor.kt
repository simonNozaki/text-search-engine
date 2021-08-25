
const val DIR_INDEX = "index"

const val DIR_SOURCE = "source"

fun main() {
    try {
        val basicIndexer = BasicIndexer(DIR_INDEX)
        val counts = basicIndexer.createIndex(DIR_SOURCE)

        basicIndexer.close()

        println("$counts files indexed.")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
