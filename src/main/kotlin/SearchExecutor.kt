fun main() {
    val queerer = BasicQueerer(DIR_INDEX)

    val hits = queerer.query("Java")

    println("${hits.totalHits} documents found.")

    hits.scoreDocs.forEach {
        val document = queerer.getDocument(it)

        println("File: ${document.get("filepath")}")
    }
}
