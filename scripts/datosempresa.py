import yfinance as yf
import json

def obtener_datos_empresa(ticker):
    stock = yf.Ticker(ticker)
    info = stock.info
    empresa = info.get('longName', 'N/A')
    pe_ratio = info.get('trailingPE', 'N/A')
    sector = info.get('sector', 'N/A')
    datos = {
        'empresa': empresa,
        'pe_ratio': pe_ratio,
        'sector': sector
    }
    return json.dumps(datos)

if __name__ == '__main__':
    ticker = 'MMM'  # Ticker de ejemplo
    datos = obtener_datos_empresa(ticker)
    print(datos)  # Imprime los datos como JSON
