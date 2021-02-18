from urllib.request import urlopen


url = "https://sofifa.com/calculator"

page = urlopen(url)

html_bytes = page.read()
html = html_bytes.decode("utf-8")
print(html)