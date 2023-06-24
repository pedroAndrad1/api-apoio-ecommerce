package br.com.uniriotec.sagui.model;

public interface ToModel<I,O> {
    public O execute(I entity);
}
