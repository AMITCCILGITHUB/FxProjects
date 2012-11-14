/* I am automatically generated.  Editing me would be pointless,
   but I wont stop you if you so desire. */

package au.notzed.jjdvb;

import java.nio.ByteBuffer;

abstract class DMXAbstract extends DVBNative {

	protected DMXAbstract(ByteBuffer p) {

		super(p);
	}
	// Fields
	// Native Methods
	// Public Methods
}

abstract class FEAbstract extends DVBNative {

	protected FEAbstract(ByteBuffer p) {

		super(p);
	}
	// Fields
	// Native Methods
	// Public Methods
}

abstract class DMXPESFilterParamsAbstract extends DVBNative {

	protected DMXPESFilterParamsAbstract(ByteBuffer p) {

		super(p);
	}

	// Fields
	public native short getPid();

	public native short setPid(short val);

	private native int _getInput();

	public DMXInput getInput() {

		return DMXInput.fromC(_getInput());
	}

	private native int _setInput(int val);

	public void setInput(DMXInput val) {

		_setInput(val.toC());
	}

	private native int _getOutput();

	public DMXOutput getOutput() {

		return DMXOutput.fromC(_getOutput());
	}

	private native int _setOutput(int val);

	public void setOutput(DMXOutput val) {

		_setOutput(val.toC());
	}

	private native int _getPesType();

	public DMXPESType getPesType() {

		return DMXPESType.fromC(_getPesType());
	}

	private native int _setPesType(int val);

	public void setPesType(DMXPESType val) {

		_setPesType(val.toC());
	}

	public native int getFlags();

	public native int setFlags(int val);
	// Native Methods
	// Public Methods
}

abstract class DVBFrontendParametersAbstract extends DVBNative {

	protected DVBFrontendParametersAbstract(ByteBuffer p) {

		super(p);
	}

	// Fields
	public native int getFrequency();

	public native int setFrequency(int val);

	private native int _getInversion();

	public FESpectralInversion getInversion() {

		return FESpectralInversion.fromC(_getInversion());
	}

	private native int _setInversion(int val);

	public void setInversion(FESpectralInversion val) {

		_setInversion(val.toC());
	}

	private native int _getofdmBandwidth();

	public FEBandwidth getofdmBandwidth() {

		return FEBandwidth.fromC(_getofdmBandwidth());
	}

	private native int _setofdmBandwidth(int val);

	public void setofdmBandwidth(FEBandwidth val) {

		_setofdmBandwidth(val.toC());
	}

	private native int _getofdmCodeRateHP();

	public FECodeRate getofdmCodeRateHP() {

		return FECodeRate.fromC(_getofdmCodeRateHP());
	}

	private native int _setofdmCodeRateHP(int val);

	public void setofdmCodeRateHP(FECodeRate val) {

		_setofdmCodeRateHP(val.toC());
	}

	private native int _getofdmCodeRateLP();

	public FECodeRate getofdmCodeRateLP() {

		return FECodeRate.fromC(_getofdmCodeRateLP());
	}

	private native int _setofdmCodeRateLP(int val);

	public void setofdmCodeRateLP(FECodeRate val) {

		_setofdmCodeRateLP(val.toC());
	}

	private native int _getofdmConstellation();

	public FEModulation getofdmConstellation() {

		return FEModulation.fromC(_getofdmConstellation());
	}

	private native int _setofdmConstellation(int val);

	public void setofdmConstellation(FEModulation val) {

		_setofdmConstellation(val.toC());
	}

	private native int _getofdmTransmissionMode();

	public FETransmitMode getofdmTransmissionMode() {

		return FETransmitMode.fromC(_getofdmTransmissionMode());
	}

	private native int _setofdmTransmissionMode(int val);

	public void setofdmTransmissionMode(FETransmitMode val) {

		_setofdmTransmissionMode(val.toC());
	}

	private native int _getofdmGuardInterval();

	public FEGuardInterval getofdmGuardInterval() {

		return FEGuardInterval.fromC(_getofdmGuardInterval());
	}

	private native int _setofdmGuardInterval(int val);

	public void setofdmGuardInterval(FEGuardInterval val) {

		_setofdmGuardInterval(val.toC());
	}

	private native int _getofdmHierarchyInformation();

	public FEHierarchy getofdmHierarchyInformation() {

		return FEHierarchy.fromC(_getofdmHierarchyInformation());
	}

	private native int _setofdmHierarchyInformation(int val);

	public void setofdmHierarchyInformation(FEHierarchy val) {

		_setofdmHierarchyInformation(val.toC());
	}
	// Native Methods
	// Public Methods
}
