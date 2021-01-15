package com.dollarsbank.utility;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendingObjectOutputStream extends ObjectOutputStream {

    private boolean append;
    private boolean initialized;
    private DataOutputStream dataOutput;

    protected AppendingObjectOutputStream(boolean append) throws IOException, SecurityException {
        super();
        this.append = append;
        this.initialized = true;
    }

    public AppendingObjectOutputStream(OutputStream out, boolean append) throws IOException {
        super(out);
        this.append = append;
        this.initialized = true;
        this.dataOutput = new DataOutputStream(out);
        this.writeStreamHeader();
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        if (!this.initialized || this.append) return;
        if (dataOutput != null) {
            dataOutput.writeShort(STREAM_MAGIC);
            dataOutput.writeShort(STREAM_VERSION);
        }
    }

}
