package br.com.uniriotec.sagui.service;

import br.com.uniriotec.sagui.model.dto.PedidoData;

import java.util.concurrent.CompletableFuture;

public interface PedidoServico {

    CompletableFuture<PedidoData> salvarPedido(PedidoData pedidoData);
    CompletableFuture<PedidoData> pedidosFallBack(PedidoData pedidoData, RuntimeException runtimeException);
}
