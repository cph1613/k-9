package com.fsck.k9.message.html

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Cleaner
import org.jsoup.safety.Whitelist as AllowList

class HtmlSanitizer {
    private val headCleaner: HeadCleaner
    private val cleaner: Cleaner

    init {
        val allowList = AllowList.relaxed()
            .addTags("font", "hr", "ins", "del", "center", "map", "area")
            .addAttributes("font", "color", "face", "size")
            .addAttributes(
                "table", "align", "background", "bgcolor", "border", "cellpadding", "cellspacing",
                "width"
            )
            .addAttributes("tr", "align", "background", "bgcolor", "valign")
            .addAttributes(
                "th",
                "align", "background", "bgcolor", "colspan", "headers", "height", "nowrap", "rowspan", "scope",
                "sorted", "valign", "width"
            )
            .addAttributes(
                "td",
                "align", "background", "bgcolor", "colspan", "headers", "height", "nowrap", "rowspan", "scope",
                "valign", "width"
            )
            .addAttributes("map", "name")
            .addAttributes("area", "shape", "coords", "href", "alt")
            .addProtocols("area", "href", "http", "https")
            .addAttributes("img", "usemap")
            .addAttributes(":all", "class", "style", "id", "dir")
            .addProtocols("img", "src", "http", "https", "cid", "data")
            .addProtocols("a", "href", "tel", "sip", "bitcoin", "ethereum", "rtsp")

        cleaner = Cleaner(allowList)
        headCleaner = HeadCleaner()
    }

    fun sanitize(html: String?): Document {
        val dirtyDocument = Jsoup.parse(html)
        val cleanedDocument = cleaner.clean(dirtyDocument)
        headCleaner.clean(dirtyDocument, cleanedDocument)
        return cleanedDocument
    }
}
