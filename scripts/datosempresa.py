import yfinance as yf
import pandas as pd
import json
import sys

def obtener_datos_empresas():
    try:
        url = "https://en.wikipedia.org/wiki/List_of_S%26P_500_companies"
        sp500_table = pd.read_html(url)
        sp500_df = sp500_table[0]
    except Exception:
        return json.dumps([])

    tickers = sp500_df["Symbol"].tolist()
    names = sp500_df["Security"].tolist()
    ticker_to_name = dict(zip(tickers, names))

    datos_empresas = []
    for ticker in tickers:
        try:
            stock = yf.Ticker(ticker)
            info = stock.info
            datos_empresas.append({
                "Ticker": ticker,
                "Empresa": ticker_to_name.get(ticker, "N/A"),
                "PER": info.get("trailingPE", None),
                "Sector": info.get("sector", "Desconocido"),
                "Industria": info.get("industry", "Desconocido"),
                "Capitalización": info.get("marketCap", None),
                "Dividendos": info.get("dividendYield", None),
                "Precio Actual": info.get("currentPrice", None),
                "Beta": info.get("beta", None),
                "EV/EBITDA": info.get("enterpriseToEbitda", None)
            })
        except Exception:
            pass

    return json.dumps(datos_empresas, ensure_ascii=False)

if __name__ == "__main__":
    sys.stdout.reconfigure(encoding="utf-8")  # Forzar salida en UTF-8
    resultado = obtener_datos_empresas()
    print(resultado)
