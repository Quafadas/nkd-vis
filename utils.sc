def generateProbabilities(length : Int) = {
	(1 to length).map( i => 1- (i.toDouble / length - 1.0/(2.0*length)))
}

def attachProbs(in: Seq[Double]) = {
	val probs = generateProbabilities(in.length)
	probs zip in
}
def attachProbs2(in: Seq[(Double, Double)]) = {
	val probs = generateProbabilities(in.length)
	val sigh = probs zip in
	sigh.map({case(d1, (d2, d3)) => (d1, d2, d3)})
	
}