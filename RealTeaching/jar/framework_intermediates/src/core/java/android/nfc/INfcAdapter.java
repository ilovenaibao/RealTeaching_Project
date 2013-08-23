/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/android/nfc/INfcAdapter.aidl
 */
package android.nfc;
/**
 * @hide
 */
public interface INfcAdapter extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.nfc.INfcAdapter
{
private static final java.lang.String DESCRIPTOR = "android.nfc.INfcAdapter";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.nfc.INfcAdapter interface,
 * generating a proxy if needed.
 */
public static android.nfc.INfcAdapter asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.nfc.INfcAdapter))) {
return ((android.nfc.INfcAdapter)iin);
}
return new android.nfc.INfcAdapter.Stub.Proxy(obj);
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
case TRANSACTION_getNfcTagInterface:
{
data.enforceInterface(DESCRIPTOR);
android.nfc.INfcTag _result = this.getNfcTagInterface();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getNfcAdapterExtrasInterface:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
android.nfc.INfcAdapterExtras _result = this.getNfcAdapterExtrasInterface(_arg0);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_disable:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.disable(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enable:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.enable();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enableNdefPush:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.enableNdefPush();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_disableNdefPush:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.disableNdefPush();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isNdefPushEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isNdefPushEnabled();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setForegroundDispatch:
{
data.enforceInterface(DESCRIPTOR);
android.app.PendingIntent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.app.PendingIntent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.content.IntentFilter[] _arg1;
_arg1 = data.createTypedArray(android.content.IntentFilter.CREATOR);
android.nfc.TechListParcel _arg2;
if ((0!=data.readInt())) {
_arg2 = android.nfc.TechListParcel.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
this.setForegroundDispatch(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_setNdefPushCallback:
{
data.enforceInterface(DESCRIPTOR);
android.nfc.INdefPushCallback _arg0;
_arg0 = android.nfc.INdefPushCallback.Stub.asInterface(data.readStrongBinder());
this.setNdefPushCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_dispatch:
{
data.enforceInterface(DESCRIPTOR);
android.nfc.Tag _arg0;
if ((0!=data.readInt())) {
_arg0 = android.nfc.Tag.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.dispatch(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setP2pModes:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setP2pModes(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements android.nfc.INfcAdapter
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
public android.nfc.INfcTag getNfcTagInterface() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.nfc.INfcTag _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getNfcTagInterface, _data, _reply, 0);
_reply.readException();
_result = android.nfc.INfcTag.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public android.nfc.INfcAdapterExtras getNfcAdapterExtrasInterface(java.lang.String pkg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.nfc.INfcAdapterExtras _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pkg);
mRemote.transact(Stub.TRANSACTION_getNfcAdapterExtrasInterface, _data, _reply, 0);
_reply.readException();
_result = android.nfc.INfcAdapterExtras.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean disable(boolean saveState) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((saveState)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_disable, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean enable() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_enable, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean enableNdefPush() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_enableNdefPush, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean disableNdefPush() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disableNdefPush, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean isNdefPushEnabled() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isNdefPushEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setForegroundDispatch(android.app.PendingIntent intent, android.content.IntentFilter[] filters, android.nfc.TechListParcel techLists) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((intent!=null)) {
_data.writeInt(1);
intent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeTypedArray(filters, 0);
if ((techLists!=null)) {
_data.writeInt(1);
techLists.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setForegroundDispatch, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setNdefPushCallback(android.nfc.INdefPushCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setNdefPushCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void dispatch(android.nfc.Tag tag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((tag!=null)) {
_data.writeInt(1);
tag.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_dispatch, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setP2pModes(int initatorModes, int targetModes) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(initatorModes);
_data.writeInt(targetModes);
mRemote.transact(Stub.TRANSACTION_setP2pModes, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getNfcTagInterface = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getNfcAdapterExtrasInterface = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_disable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_enable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_enableNdefPush = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_disableNdefPush = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_isNdefPushEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_setForegroundDispatch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setNdefPushCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_dispatch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_setP2pModes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
}
public android.nfc.INfcTag getNfcTagInterface() throws android.os.RemoteException;
public android.nfc.INfcAdapterExtras getNfcAdapterExtrasInterface(java.lang.String pkg) throws android.os.RemoteException;
public int getState() throws android.os.RemoteException;
public boolean disable(boolean saveState) throws android.os.RemoteException;
public boolean enable() throws android.os.RemoteException;
public boolean enableNdefPush() throws android.os.RemoteException;
public boolean disableNdefPush() throws android.os.RemoteException;
public boolean isNdefPushEnabled() throws android.os.RemoteException;
public void setForegroundDispatch(android.app.PendingIntent intent, android.content.IntentFilter[] filters, android.nfc.TechListParcel techLists) throws android.os.RemoteException;
public void setNdefPushCallback(android.nfc.INdefPushCallback callback) throws android.os.RemoteException;
public void dispatch(android.nfc.Tag tag) throws android.os.RemoteException;
public void setP2pModes(int initatorModes, int targetModes) throws android.os.RemoteException;
}
