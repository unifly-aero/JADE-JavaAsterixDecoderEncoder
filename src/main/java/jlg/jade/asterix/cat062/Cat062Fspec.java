/* 
* Created by dan-geabunea on 4/19/2016.
* This code is the property of JLG Consulting. Please
* check the license terms for this product to see under what
* conditions you can use or modify this source code.
*/
package jlg.jade.asterix.cat062;

import jlg.jade.common.AsterixDecodingException;
import jlg.jade.common.DebugMessageSource;
import jlg.jade.common.Decodable;
import jlg.jade.common.Encodable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.BitSet;

/**
 * This class maps the FSPEC structure of Asterix Cat-062. Internally, it uses an array of booleans that
 * are true if the item is in the message and false otherwise. You can use this array in conjunction with the
 * Cat062UAP enum to make code more understandable.
 */
public class Cat062Fspec extends DebugMessageSource implements Decodable, Encodable {
    private final boolean[] fspecList;

    public Cat062Fspec(){
        final int FSPEC_LENGTH = 40;
        this.fspecList = new boolean[FSPEC_LENGTH];
    }

    @Override
    public int decode(byte[] input, int offset, int inputLength){
        appendDebugMsg("## FSPEC Data:");

        final int FSPEC_MAX_BYTES = 5;
        final int BYTE_LENGTH = 8;

        BitSet bs;
        for(int i=0;i<FSPEC_MAX_BYTES;i++){
            int fspecOctet = Byte.toUnsignedInt(input[offset+i]);
            appendDebugMsg(String.format("%-5s %-1s value: %-10s", "Octet",i+1, fspecOctet));
            appendNewLine();

            bs = BitSet.valueOf(new byte[]{input[offset+i]});
            for(int j=0;j<BYTE_LENGTH;j++){
                fspecList[j + (i*8)] = bs.get(j);
                if(bs.get(j)) {
                    appendDebugMsg("Cat062 " + Cat062UAP.values()[j + (i * 8)] + " has been added to FSPEC with a value of 1");
                    appendNewLine();
                }
            }

            //field extension is 0 => do not continue
            if(!bs.get(0)){
                offset += i+1;
                return offset;
            }
        }
        //the FXBIT was never 0, so there is a problem with the FSPEC
        throw new AsterixDecodingException.UndeterminedFspecEnd();
    }

    @Override
    public int encode(byte[] dest, int offset) {
        throw new NotImplementedException();
    }

    /**
     * Checks if the given cat062 item exists in the FSPEC array.
     * @param cat062Item the cat 062 item to verify
     * @return true, if the given item exists in the FSPEC, false otherwise
     */
    public boolean isItemInFspec(Cat062UAP cat062Item){
        return fspecList[cat062Item.ordinal()];
    }

    public int getLength(){
        return fspecList.length;
    }
}