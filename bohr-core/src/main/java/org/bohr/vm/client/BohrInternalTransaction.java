/**
 * Copyright (c) 2019 The Bohr Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.bohr.vm.client;

import org.bohr.core.Amount;
import org.bohr.crypto.Hex;
import org.bohr.util.SimpleDecoder;
import org.bohr.util.SimpleEncoder;
import org.ethereum.vm.OpCode;
import org.ethereum.vm.program.InternalTransaction;

/**
 * Represents a Bohr-flavored internal transaction.
 */
public class BohrInternalTransaction {

    private byte[] rootTxHash;
    private boolean rejected;
    private int depth;
    private int index;
    private String type;

    private byte[] from;
    private byte[] to;
    private long nonce;
    private Amount value;
    private byte[] data;
    private long gas;
    private Amount gasPrice;

    public BohrInternalTransaction(byte[] rootTxHash, InternalTransaction it) {
        this(rootTxHash, it.isRejected(), it.getDepth(), it.getIndex(), it.getType(),
                it.getFrom(), it.getTo(), it.getNonce(),
                Conversion.weiToAmount(it.getValue()),
                it.getData(), it.getGas(),
                Conversion.weiToAmount(it.getGasPrice()));
    }

    public BohrInternalTransaction(byte[] rootTxHash, boolean rejected, int depth, int index, String type, byte[] from, byte[] to,
            long nonce,
            Amount value, byte[] data, long gas, Amount gasPrice) {
        this.rootTxHash = rootTxHash;
        this.rejected = rejected;
        this.depth = depth;
        this.index = index;
        this.type = type;
        this.from = from;
        this.to = to;
        this.nonce = nonce;
        this.value = value;
        this.data = data;
        this.gas = gas;
        this.gasPrice = gasPrice;
    }

    public byte[] getRootTxHash() {
        return rootTxHash;
    }

    public boolean isRejected() {
        return rejected;
    }

    public int getDepth() {
        return depth;
    }

    public int getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public byte[] getFrom() {
        return from;
    }

    public byte[] getTo() {
        return to;
    }

    public long getNonce() {
        return nonce;
    }

    public Amount getValue() {
        return value;
    }

    public byte[] getData() {
        return data;
    }

    public long getGas() {
        return gas;
    }

    public Amount getGasPrice() {
        return gasPrice;
    }

    public byte[] toBytes() {
        SimpleEncoder enc = new SimpleEncoder();
        enc.writeBytes(this.getRootTxHash());
        enc.writeBoolean(this.isRejected());
        enc.writeInt(this.getDepth());
        enc.writeInt(this.getIndex());
        enc.writeString(this.getType());
        enc.writeBytes(this.getFrom());
        enc.writeBytes(this.getTo());
        enc.writeLong(this.getNonce());
        enc.writeAmount(this.getValue());
        enc.writeBytes(this.getData());
        enc.writeLong(this.getGas());
        enc.writeAmount(this.getGasPrice());

        return enc.toBytes();
    }

    public static BohrInternalTransaction fromBytes(byte[] bytes) {
        SimpleDecoder dec = new SimpleDecoder(bytes);
        byte[] rootTxHash = dec.readBytes();
        boolean isRejected = dec.readBoolean();
        int depth = dec.readInt();
        int index = dec.readInt();
        String type = dec.readString();
        byte[] from = dec.readBytes();
        byte[] to = dec.readBytes();
        long nonce = dec.readLong();
        Amount value = dec.readAmount();
        byte[] data = dec.readBytes();
        long gas = dec.readLong();
        Amount gasPrice = dec.readAmount();

        return new BohrInternalTransaction(rootTxHash, isRejected, depth, index,
                type, from, to, nonce, value, data, gas, gasPrice);
    }

    @Override
    public String toString() {
        return "BohrInternalTransaction{" +
                "rejected=" + rejected +
                ", depth=" + depth +
                ", index=" + index +
                ", type=" + type +
                ", from=" + Hex.encode(from) +
                ", to=" + Hex.encode(to) +
                ", nonce=" + nonce +
                ", value=" + value +
                ", data=" + Hex.encode(data) +
                ", gas=" + gas +
                ", gasPrice=" + gasPrice +
                '}';
    }
}
