package DTOs;

public class CurrencyConverterResponseDTO {
    private Float exchangeRate;

    public void setExchangeRate(float exRate){
        this.exchangeRate = exRate;
    }

    public float getExchangeRate(){
        return this.exchangeRate;
    }
}
