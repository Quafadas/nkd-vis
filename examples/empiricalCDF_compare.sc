import $exec.^.nkd
import $file.^.utils
import $ivy.`com.nrinaudo::kantan.csv:0.6.1`
import kantan.csv._
import kantan.csv.ops._ 
import scala.io

val test = ujson.read(io.Source.fromFile("DensityPlot_flipAxis_compare.txt").mkString(""))
val reader = io.Source.fromFile("test.csv").mkString.asCsvReader[Double](rfc.withHeader)
val reader2 = io.Source.fromFile("test2.csv").mkString.asCsvReader[Double](rfc.withHeader)
val dist = reader.toVector.map(_.getOrElse(Double.NaN))
val dist2 = reader2.toVector.map(_.getOrElse(Double.NaN))
val dists = dist zip dist2
val withProb = utils.attachProbs2(dists) 

val jsonArr = withProb.map({case(p, v1, v2) => {
			List(
				ujson.Obj("probability" -> p, "tret" -> v1, "c" ->"Portfolio 1", "offset" -> 0), 
				ujson.Obj("probability" -> p, "tret" -> v2, "c" ->"Portfolio 2", "offset" -> 0.5)
			)
		}
	}
).flatten

val asOjb = ujson.Obj("name"  -> "points", "values" ->  jsonArr)


test("data")(0) = asOjb
//println(ujson.write(asOjb, indent=5))

plot(fixAppearence(test), "ECDF")

