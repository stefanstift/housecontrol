package com.stift.housecontrol.event;

import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.KNXFormatException;
import tuwien.auto.calimero.dptxlator.*;
import tuwien.auto.calimero.process.ProcessCommunication;

import java.util.Arrays;
import java.util.Objects;

public class GroupAddressEvent {

    private final String source;
    private final GroupAddress destination;
    private final byte[] data;

    public enum Type {
        UNDEFINED, GROUP_READ, GROUP_RESPONSE, GROUP_WRITE
    }

    ;

    private final Type type;

    public GroupAddressEvent(String source, GroupAddress destination, Type type, byte[] data) {
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.data = data;
    }

    /**
     * Gets the source of the group address event.
     *
     * @return indiviudual address of event source, format "x.y.z"
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the destination of the event
     *
     * @return group address, format "x/y/z"
     */
    public GroupAddress getDestination() {
        return destination;
    }

    /**
     * Returns the raw data that is sent to the group adress
     *
     * @return application layer service data unit (ASDU)
     */
    public byte[] getData() {
        return data;
    }

    /**
     * The type of the event. See GroupAddressEvent#Type
     *
     * @return
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the data of the received event as boolean datapoint value.
     * <p>
     *
     * @return the received value of type boolean
     */
    public boolean asBool() throws KNXFormatException {
        final DPTXlatorBoolean t = new DPTXlatorBoolean(DPTXlatorBoolean.DPT_BOOL);
        t.setData(data);
        return t.getValueBoolean();
    }

    /**
     * Returns the data of the received event as 8 Bit signed, unscaled value DPT
     * 5.005 DPT 5.010
     *
     * @return 8 bit value 0..255
     */
    public int asUnscaled() throws KNXFormatException {
        final DPTXlator8BitUnsigned t = new DPTXlator8BitUnsigned(ProcessCommunication.UNSCALED);
        t.setData(data);
        return t.getValueUnsigned();
    }

    /**
     * Returns the data of the received event as 8 Bit signed, 0..100 scaled (f.i.
     * %) value. DPT 5.001
     *
     * @return 8 bit value 0..255 mapped to 0..100, f.i. %
     */
    public int asScaled() throws KNXFormatException {
        final DPTXlator8BitUnsigned t = new DPTXlator8BitUnsigned(ProcessCommunication.SCALING);
        t.setData(data);
        return t.getValueUnsigned();
    }

    /**
     * Returns the data of the received event as 8 Bit signed, angle scaled value.
     * DPT 5.003
     *
     * @return 8 bit value 0..255 mapped to 0..360°
     */
    public int asAngle() throws KNXFormatException {
        final DPTXlator8BitUnsigned t = new DPTXlator8BitUnsigned(ProcessCommunication.ANGLE);
        t.setData(data);
        return t.getValueUnsigned();
    }

    /**
     * Returns the data of the received event as 3 Bit controlled datapoint value.
     * DPT 3.007
     *
     * @return the received value of type 3 Bit controlled
     */
    public int asControl() throws KNXFormatException {
        final DPTXlator3BitControlled t = new DPTXlator3BitControlled(
                DPTXlator3BitControlled.DPT_CONTROL_DIMMING);
        t.setData(data);
        return t.getValueSigned();
    }

    /**
     * Returns the data of the received event as 8bit signed value. DPT 6
     *
     * @return the received value of type integer, containing only 8 bit signed
     */
    public int asDpt6() throws KNXFormatException {
        final DPTXlator8BitSigned t = new DPTXlator8BitSigned(DPTXlator8BitSigned.DPT_VALUE_1_UCOUNT);
        t.setData(data);
        return (int) t.getNumericValue();
    }

    /**
     * Returns the data of the received event as 2-byte KNX float value. DPT
     * 9.xxx, i.e. 9.001 for temperature
     *
     * @return the received value of type float
     */
    public float as2ByteFloat() throws KNXFormatException {
        final DPTXlator2ByteFloat t = new DPTXlator2ByteFloat(
                DPTXlator2ByteFloat.DPT_RAIN_AMOUNT /* using rainamount to get full 2byte spectrum */);
        t.setData(data);
        return (float) t.getNumericValue();
    }

    public float as2ByteFloat(DPTXlator2ByteFloat t) throws KNXFormatException {
        t.setData(data);
        return (float) t.getNumericValue();
    }


    /**
     * Returns the data of the received event as 4-byte KNX float value. DPT
     * 14.xxx, i.e. 14.070 for temperature difference
     *
     * @return the received value of type double
     */
    public double as4ByteFloat() throws KNXFormatException {
            final DPTXlator4ByteFloat t = new DPTXlator4ByteFloat(
                    DPTXlator4ByteFloat.DPT_TEMPERATURE_DIFFERENCE);
            t.setData(data);
            return t.getValueFloat();
    }

    /**
     * Returns the data of the received event as string value. DPT16.001
     * <p>
     * The used character set is ISO-8859-1 (Latin 1), with an allowed string
     * length of 14 characters.
     *
     * @return the received value of type String
     */
    public String asString() throws KNXFormatException {
            final DPTXlatorString t = new DPTXlatorString(DPTXlatorString.DPT_STRING_8859_1);
            t.setData(data);
            return t.getValue();
    }

    /**
     * Returns the data of the received event as datapoint value of the requested
     * DPT in String representation.
     *
     * @param dptMainNumber datapoint type main number, number >= 0; use 0 to
     *                      infer translator type from <code>dptID</code> argument only
     * @param dptID         datapoint type ID for selecting a particular kind of value
     *                      translation
     * @return the received value of the requested type as String representation
     */
    public String asString(final int dptMainNumber, final String dptID) throws KNXException {
            final DPTXlator t = TranslatorTypes.createTranslator(dptMainNumber, dptID);
            t.setData(data);
            return t.getValue();
    }

    @Override
    public String toString() {
        return "GroupAddressEvent{" + "source=" + source + ", destination=" + destination + ", data=" + Arrays.toString(data) + ", type=" + type + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.source);
        hash = 41 * hash + Objects.hashCode(this.destination);
        hash = 41 * hash + Arrays.hashCode(this.data);
        hash = 41 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GroupAddressEvent other = (GroupAddressEvent) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        if (!Arrays.equals(this.data, other.data)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

}
