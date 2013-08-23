/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/android/view/IDisplayManager.aidl
 */
package android.view;
/**
 * System private interface to the window manager.
 *
 * {@hide}
 */
public interface IDisplayManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.view.IDisplayManager
{
private static final java.lang.String DESCRIPTOR = "android.view.IDisplayManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.view.IDisplayManager interface,
 * generating a proxy if needed.
 */
public static android.view.IDisplayManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.view.IDisplayManager))) {
return ((android.view.IDisplayManager)iin);
}
return new android.view.IDisplayManager.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getDisplayCount:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDisplayCount();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayOpenStatus:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.getDisplayOpenStatus(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDisplayHotPlugStatus:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayHotPlugStatus(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayOutputType:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayOutputType(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayOutputFormat:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayOutputFormat(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayWidth:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayWidth(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayHeight:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayHeight(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayParameter:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _result = this.setDisplayParameter(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayPixelFormat:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayPixelFormat(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.setDisplayMode(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDisplayMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayOutputType:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _result = this.setDisplayOutputType(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_openDisplay:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.openDisplay(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_closeDisplay:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.closeDisplay(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayMaster:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.setDisplayMaster(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayMaster:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDisplayMaster();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getMaxWidthDisplay:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMaxWidthDisplay();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getMaxHdmiMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMaxHdmiMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayBacklightMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.setDisplayBacklightMode(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayBacklightMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDisplayBacklightMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isSupportHdmiMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.isSupportHdmiMode(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isSupport3DMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.isSupport3DMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getHdmiHotPlugStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getHdmiHotPlugStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getTvHotPlugStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getTvHotPlugStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayAreaPercent:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.setDisplayAreaPercent(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayAreaPercent:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayAreaPercent(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayBright:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.setDisplayBright(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayBright:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayBright(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayContrast:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.setDisplayContrast(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayContrast:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayContrast(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplaySaturation:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.setDisplaySaturation(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplaySaturation:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplaySaturation(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDisplayHue:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.setDisplayHue(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisplayHue:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getDisplayHue(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_startWifiDisplaySend:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.startWifiDisplaySend(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_endWifiDisplaySend:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.endWifiDisplaySend(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_startWifiDisplayReceive:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.startWifiDisplayReceive(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_endWifiDisplayReceive:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.endWifiDisplayReceive(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateSendClient:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.updateSendClient(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements android.view.IDisplayManager
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public int getDisplayCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDisplayCount, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean getDisplayOpenStatus(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_getDisplayOpenStatus, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayHotPlugStatus(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_getDisplayHotPlugStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayOutputType(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_getDisplayOutputType, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayOutputFormat(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_getDisplayOutputFormat, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayWidth(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_getDisplayWidth, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayHeight(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_getDisplayHeight, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayParameter(int mDisplay, int param0, int param1) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
_data.writeInt(param0);
_data.writeInt(param1);
mRemote.transact(Stub.TRANSACTION_setDisplayParameter, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayPixelFormat(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_getDisplayPixelFormat, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayMode(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setDisplayMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDisplayMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayOutputType(int mDisplay, int type, int format) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
_data.writeInt(type);
_data.writeInt(format);
mRemote.transact(Stub.TRANSACTION_setDisplayOutputType, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int openDisplay(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_openDisplay, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int closeDisplay(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_closeDisplay, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayMaster(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_setDisplayMaster, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayMaster() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDisplayMaster, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMaxWidthDisplay() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMaxWidthDisplay, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMaxHdmiMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMaxHdmiMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayBacklightMode(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setDisplayBacklightMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayBacklightMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDisplayBacklightMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int isSupportHdmiMode(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_isSupportHdmiMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int isSupport3DMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSupport3DMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getHdmiHotPlugStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getHdmiHotPlugStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getTvHotPlugStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTvHotPlugStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayAreaPercent(int displayno, int percent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
_data.writeInt(percent);
mRemote.transact(Stub.TRANSACTION_setDisplayAreaPercent, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayAreaPercent(int displayno) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
mRemote.transact(Stub.TRANSACTION_getDisplayAreaPercent, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayBright(int displayno, int bright) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
_data.writeInt(bright);
mRemote.transact(Stub.TRANSACTION_setDisplayBright, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayBright(int displayno) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
mRemote.transact(Stub.TRANSACTION_getDisplayBright, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayContrast(int displayno, int contrast) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
_data.writeInt(contrast);
mRemote.transact(Stub.TRANSACTION_setDisplayContrast, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayContrast(int displayno) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
mRemote.transact(Stub.TRANSACTION_getDisplayContrast, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplaySaturation(int displayno, int saturation) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
_data.writeInt(saturation);
mRemote.transact(Stub.TRANSACTION_setDisplaySaturation, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplaySaturation(int displayno) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
mRemote.transact(Stub.TRANSACTION_getDisplaySaturation, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int setDisplayHue(int displayno, int hue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
_data.writeInt(hue);
mRemote.transact(Stub.TRANSACTION_setDisplayHue, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDisplayHue(int displayno) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(displayno);
mRemote.transact(Stub.TRANSACTION_getDisplayHue, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int startWifiDisplaySend(int mDisplay, int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_startWifiDisplaySend, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int endWifiDisplaySend(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_endWifiDisplaySend, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int startWifiDisplayReceive(int mDisplay, int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_startWifiDisplayReceive, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int endWifiDisplayReceive(int mDisplay) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mDisplay);
mRemote.transact(Stub.TRANSACTION_endWifiDisplayReceive, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int updateSendClient(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_updateSendClient, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getDisplayCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getDisplayOpenStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getDisplayHotPlugStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getDisplayOutputType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getDisplayOutputFormat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getDisplayWidth = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getDisplayHeight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setDisplayParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getDisplayPixelFormat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setDisplayMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getDisplayMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_setDisplayOutputType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_openDisplay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_closeDisplay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_setDisplayMaster = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getDisplayMaster = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getMaxWidthDisplay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_getMaxHdmiMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_setDisplayBacklightMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_getDisplayBacklightMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_isSupportHdmiMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_isSupport3DMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getHdmiHotPlugStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_getTvHotPlugStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_setDisplayAreaPercent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_getDisplayAreaPercent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_setDisplayBright = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_getDisplayBright = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_setDisplayContrast = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_getDisplayContrast = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_setDisplaySaturation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_getDisplaySaturation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_setDisplayHue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_getDisplayHue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_startWifiDisplaySend = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_endWifiDisplaySend = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_startWifiDisplayReceive = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_endWifiDisplayReceive = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_updateSendClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
}
public int getDisplayCount() throws android.os.RemoteException;
public boolean getDisplayOpenStatus(int mDisplay) throws android.os.RemoteException;
public int getDisplayHotPlugStatus(int mDisplay) throws android.os.RemoteException;
public int getDisplayOutputType(int mDisplay) throws android.os.RemoteException;
public int getDisplayOutputFormat(int mDisplay) throws android.os.RemoteException;
public int getDisplayWidth(int mDisplay) throws android.os.RemoteException;
public int getDisplayHeight(int mDisplay) throws android.os.RemoteException;
public int setDisplayParameter(int mDisplay, int param0, int param1) throws android.os.RemoteException;
public int getDisplayPixelFormat(int mDisplay) throws android.os.RemoteException;
public int setDisplayMode(int mode) throws android.os.RemoteException;
public int getDisplayMode() throws android.os.RemoteException;
public int setDisplayOutputType(int mDisplay, int type, int format) throws android.os.RemoteException;
public int openDisplay(int mDisplay) throws android.os.RemoteException;
public int closeDisplay(int mDisplay) throws android.os.RemoteException;
public int setDisplayMaster(int mDisplay) throws android.os.RemoteException;
public int getDisplayMaster() throws android.os.RemoteException;
public int getMaxWidthDisplay() throws android.os.RemoteException;
public int getMaxHdmiMode() throws android.os.RemoteException;
public int setDisplayBacklightMode(int mode) throws android.os.RemoteException;
public int getDisplayBacklightMode() throws android.os.RemoteException;
public int isSupportHdmiMode(int mode) throws android.os.RemoteException;
public int isSupport3DMode() throws android.os.RemoteException;
public int getHdmiHotPlugStatus() throws android.os.RemoteException;
public int getTvHotPlugStatus() throws android.os.RemoteException;
public int setDisplayAreaPercent(int displayno, int percent) throws android.os.RemoteException;
public int getDisplayAreaPercent(int displayno) throws android.os.RemoteException;
public int setDisplayBright(int displayno, int bright) throws android.os.RemoteException;
public int getDisplayBright(int displayno) throws android.os.RemoteException;
public int setDisplayContrast(int displayno, int contrast) throws android.os.RemoteException;
public int getDisplayContrast(int displayno) throws android.os.RemoteException;
public int setDisplaySaturation(int displayno, int saturation) throws android.os.RemoteException;
public int getDisplaySaturation(int displayno) throws android.os.RemoteException;
public int setDisplayHue(int displayno, int hue) throws android.os.RemoteException;
public int getDisplayHue(int displayno) throws android.os.RemoteException;
public int startWifiDisplaySend(int mDisplay, int mode) throws android.os.RemoteException;
public int endWifiDisplaySend(int mDisplay) throws android.os.RemoteException;
public int startWifiDisplayReceive(int mDisplay, int mode) throws android.os.RemoteException;
public int endWifiDisplayReceive(int mDisplay) throws android.os.RemoteException;
public int updateSendClient(int mode) throws android.os.RemoteException;
}
