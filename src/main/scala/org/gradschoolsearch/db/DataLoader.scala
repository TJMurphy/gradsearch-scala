package org.gradschoolsearch.db

/**
 * Created by russell on 9/1/14.
 */

import org.gradschoolsearch.models.Professor
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.io.Source
object DataLoader {
  case class ProfWithKeywords(professor: Professor, keywords: List[String])
  private case class InternalFormat(school: String, name: String, title: String, image: String, source: String,
                                     keywords: List[String], department: String)
  val dataFields = List("src/main/resources/data/yale.dat.json")
  implicit val formats = DefaultFormats
  def loadData() = {
    println(new java.io.File(".").getAbsolutePath())
    dataFields.flatMap { fileName =>
      val file = Source.fromFile(fileName).mkString
      val json = parse(file)
      val professorsInternal = json.extract[List[InternalFormat]]
      professorsInternal.map(internal => ProfWithKeywords(
        Professor(None, internal.name, internal.school, internal.department),
        internal.keywords
      ))
    }
  }
}
