import $exec.^.nkd

val test = ujson.read(requests.get("https://vega.github.io/vega/examples/bar-chart.vg.json").text)

println(ujson.write(test("data"), indent=4))

plot(fixAppearence(test), "Bar Chart")

test("data")(0)("values") = ujson.Arr(
	ujson.Obj("category" -> "Epic", "amount" -> 50),
	ujson.Obj("category" -> "amounts", "amount" -> 100)
)

plot(fixAppearence(test), "Bar Chart")