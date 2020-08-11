# nkd-vis
This library is designed to be _very_ simple - it's a hack instead of a library :-), based on the following idea.

1. Fetch your spec from the Vega examples gallery.
1. Parse it using your favourite json library.
1. Replace the parts you want by noodling around with the JSON
1. Pipe the spec out to a temporary file and open it in the browser

In combination with ammonites "watch" mode, this worked well enough for me to be happy noodling  around with my dataset. It also has the attraction that it's embeddable in any other place you would want to use it afterwards...

## Examples
### Simple bar chart
```scala
import $exec.`c:\\temp\\nkd`

val test = ujson.read(requests.get("https://vega.github.io/vega/examples/bar-chart.vg.json").text)
plot(test, "Bar Chart")
test("data")(0)("values") = ujson.Arr(
	ujson.Obj("category" -> "Epic", "amount" -> 50),
	ujson.Obj("category" -> "amounts", "amount" -> 100)
)
plot(test, "Bar Chart")
```
### Higher dimensional scattter plotting
