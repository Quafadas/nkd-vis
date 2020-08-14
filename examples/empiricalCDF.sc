import $exec.^.nkd
import $file.^.utils
import $ivy.`com.nrinaudo::kantan.csv:0.6.1`
import kantan.csv._
import kantan.csv.ops._ 
import scala.io

val test = ujson.read(io.Source.fromFile("DensityPlot_flipAxis.txt").mkString(""))
val reader = io.Source.fromFile("test.csv").mkString.asCsvReader[Double](rfc.withHeader)
val dist = reader.toVector.map(_.getOrElse(Double.NaN))
val withProb = utils.attachProbs(dist) 
val jsonArr = withProb.map({case(p, v) => ujson.Obj("probability" -> p, "tret" -> v)})
val asOjb = ujson.Obj("name"  -> "points", "values" ->  jsonArr)
//println(asOjb)
println(ujson.write(test("data"), indent=4))

test("data")(0) = asOjb

plot(fixAppearence(test), "ECDF")


