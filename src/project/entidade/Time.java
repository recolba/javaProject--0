package project.entidade;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author renato
 */
public class Time {
    private long id;
    private String nome;
    private int ano_de_fundação;
    private String cidade;
    private String estado;
    
    public Time() {}
    
    public Time(long id, String n, int anf, String c, String e) {
        this.id = id;
        nome = n;
        ano_de_fundação = anf;
        cidade = c;
        estado = e;
    }
    
    public long getId() { return id; }
    public String getNome() { return nome; }
    public int getAnf() { return ano_de_fundação; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    
    public void setId(long id) { this.id = id; }
    public void setNome(String n) { nome = n; }
    public void setAnf(int anf) { ano_de_fundação = anf; }
    public void setCidade(String c) { cidade = c; }
    public void setEstado(String e) { estado = e; }
}
