public enum SizedImagem {

    IMAGE_VERTCAL(1090, 1500),
    IMAGE_HORIZONTAL(1080, 710),
    IMAGE_LADOS_IGUAIS(800, 800);

    private int altura;
    private int largura;

    SizedImagem(int altura, int largura){

        this.altura = altura;
        this.largura = largura;
        
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    
    
}
