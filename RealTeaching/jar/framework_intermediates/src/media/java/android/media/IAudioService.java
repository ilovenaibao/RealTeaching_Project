/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/media/java/android/media/IAudioService.aidl
 */
package android.media;
/**
 * {@hide}
 */
public interface IAudioService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.media.IAudioService
{
private static final java.lang.String DESCRIPTOR = "android.media.IAudioService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.media.IAudioService interface,
 * generating a proxy if needed.
 */
public static android.media.IAudioService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.media.IAudioService))) {
return ((android.media.IAudioService)iin);
}
return new android.media.IAudioService.Stub.Proxy(obj);
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
case TRANSACTION_adjustVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.adjustVolume(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_adjustLocalOrRemoteStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.adjustLocalOrRemoteStreamVolume(_arg0, _arg1);
return true;
}
case TRANSACTION_adjustSuggestedStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.adjustSuggestedStreamVolume(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_adjustStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.adjustStreamVolume(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_adjustMasterVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.adjustMasterVolume(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.setStreamVolume(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_setRemoteStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setRemoteStreamVolume(_arg0);
return true;
}
case TRANSACTION_setMasterVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setMasterVolume(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setStreamSolo:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
android.os.IBinder _arg2;
_arg2 = data.readStrongBinder();
this.setStreamSolo(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_setStreamMute:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
android.os.IBinder _arg2;
_arg2 = data.readStrongBinder();
this.setStreamMute(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_isStreamMute:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isStreamMute(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setMasterMute:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
int _arg1;
_arg1 = data.readInt();
android.os.IBinder _arg2;
_arg2 = data.readStrongBinder();
this.setMasterMute(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_isMasterMute:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isMasterMute();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getStreamVolume(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getMasterVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMasterVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getStreamMaxVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getStreamMaxVolume(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getMasterMaxVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMasterMaxVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getLastAudibleStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getLastAudibleStreamVolume(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getLastAudibleMasterVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getLastAudibleMasterVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setRingerMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setRingerMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getRingerMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getRingerMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setStreamSpeed:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setStreamSpeed(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getStreamSpeed:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getStreamSpeed(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setVibrateSetting:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setVibrateSetting(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getVibrateSetting:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getVibrateSetting(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_shouldVibrate:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.shouldVibrate(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.os.IBinder _arg1;
_arg1 = data.readStrongBinder();
this.setMode(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_playSoundEffect:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.playSoundEffect(_arg0);
return true;
}
case TRANSACTION_playSoundEffectVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
float _arg1;
_arg1 = data.readFloat();
this.playSoundEffectVolume(_arg0, _arg1);
return true;
}
case TRANSACTION_loadSoundEffects:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.loadSoundEffects();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_unloadSoundEffects:
{
data.enforceInterface(DESCRIPTOR);
this.unloadSoundEffects();
return true;
}
case TRANSACTION_reloadAudioSettings:
{
data.enforceInterface(DESCRIPTOR);
this.reloadAudioSettings();
return true;
}
case TRANSACTION_setSpeakerphoneOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setSpeakerphoneOn(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isSpeakerphoneOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSpeakerphoneOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setBluetoothScoOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setBluetoothScoOn(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isBluetoothScoOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isBluetoothScoOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setBluetoothA2dpOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setBluetoothA2dpOn(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isBluetoothA2dpOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isBluetoothA2dpOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_requestAudioFocus:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.os.IBinder _arg2;
_arg2 = data.readStrongBinder();
android.media.IAudioFocusDispatcher _arg3;
_arg3 = android.media.IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
java.lang.String _arg4;
_arg4 = data.readString();
java.lang.String _arg5;
_arg5 = data.readString();
int _result = this.requestAudioFocus(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_abandonAudioFocus:
{
data.enforceInterface(DESCRIPTOR);
android.media.IAudioFocusDispatcher _arg0;
_arg0 = android.media.IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.abandonAudioFocus(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_unregisterAudioFocusClient:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.unregisterAudioFocusClient(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_dispatchMediaKeyEvent:
{
data.enforceInterface(DESCRIPTOR);
android.view.KeyEvent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.view.KeyEvent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.dispatchMediaKeyEvent(_arg0);
return true;
}
case TRANSACTION_dispatchMediaKeyEventUnderWakelock:
{
data.enforceInterface(DESCRIPTOR);
android.view.KeyEvent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.view.KeyEvent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.dispatchMediaKeyEventUnderWakelock(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_registerMediaButtonIntent:
{
data.enforceInterface(DESCRIPTOR);
android.app.PendingIntent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.app.PendingIntent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.content.ComponentName _arg1;
if ((0!=data.readInt())) {
_arg1 = android.content.ComponentName.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.registerMediaButtonIntent(_arg0, _arg1);
return true;
}
case TRANSACTION_unregisterMediaButtonIntent:
{
data.enforceInterface(DESCRIPTOR);
android.app.PendingIntent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.app.PendingIntent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.content.ComponentName _arg1;
if ((0!=data.readInt())) {
_arg1 = android.content.ComponentName.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.unregisterMediaButtonIntent(_arg0, _arg1);
return true;
}
case TRANSACTION_registerMediaButtonEventReceiverForCalls:
{
data.enforceInterface(DESCRIPTOR);
android.content.ComponentName _arg0;
if ((0!=data.readInt())) {
_arg0 = android.content.ComponentName.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.registerMediaButtonEventReceiverForCalls(_arg0);
return true;
}
case TRANSACTION_unregisterMediaButtonEventReceiverForCalls:
{
data.enforceInterface(DESCRIPTOR);
this.unregisterMediaButtonEventReceiverForCalls();
return true;
}
case TRANSACTION_registerRemoteControlClient:
{
data.enforceInterface(DESCRIPTOR);
android.app.PendingIntent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.app.PendingIntent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.media.IRemoteControlClient _arg1;
_arg1 = android.media.IRemoteControlClient.Stub.asInterface(data.readStrongBinder());
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.registerRemoteControlClient(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_unregisterRemoteControlClient:
{
data.enforceInterface(DESCRIPTOR);
android.app.PendingIntent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.app.PendingIntent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.media.IRemoteControlClient _arg1;
_arg1 = android.media.IRemoteControlClient.Stub.asInterface(data.readStrongBinder());
this.unregisterRemoteControlClient(_arg0, _arg1);
return true;
}
case TRANSACTION_registerRemoteControlDisplay:
{
data.enforceInterface(DESCRIPTOR);
android.media.IRemoteControlDisplay _arg0;
_arg0 = android.media.IRemoteControlDisplay.Stub.asInterface(data.readStrongBinder());
this.registerRemoteControlDisplay(_arg0);
return true;
}
case TRANSACTION_unregisterRemoteControlDisplay:
{
data.enforceInterface(DESCRIPTOR);
android.media.IRemoteControlDisplay _arg0;
_arg0 = android.media.IRemoteControlDisplay.Stub.asInterface(data.readStrongBinder());
this.unregisterRemoteControlDisplay(_arg0);
return true;
}
case TRANSACTION_remoteControlDisplayUsesBitmapSize:
{
data.enforceInterface(DESCRIPTOR);
android.media.IRemoteControlDisplay _arg0;
_arg0 = android.media.IRemoteControlDisplay.Stub.asInterface(data.readStrongBinder());
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.remoteControlDisplayUsesBitmapSize(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_setPlaybackInfoForRcc:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.setPlaybackInfoForRcc(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_getRemoteStreamMaxVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getRemoteStreamMaxVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getRemoteStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getRemoteStreamVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_registerRemoteVolumeObserverForRcc:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.media.IRemoteVolumeObserver _arg1;
_arg1 = android.media.IRemoteVolumeObserver.Stub.asInterface(data.readStrongBinder());
this.registerRemoteVolumeObserverForRcc(_arg0, _arg1);
return true;
}
case TRANSACTION_startBluetoothSco:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.startBluetoothSco(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stopBluetoothSco:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.stopBluetoothSco(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_forceVolumeControlStream:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.os.IBinder _arg1;
_arg1 = data.readStrongBinder();
this.forceVolumeControlStream(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setRingtonePlayer:
{
data.enforceInterface(DESCRIPTOR);
android.media.IRingtonePlayer _arg0;
_arg0 = android.media.IRingtonePlayer.Stub.asInterface(data.readStrongBinder());
this.setRingtonePlayer(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getRingtonePlayer:
{
data.enforceInterface(DESCRIPTOR);
android.media.IRingtonePlayer _result = this.getRingtonePlayer();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getMasterStreamType:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMasterStreamType();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setWiredDeviceConnectionState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
this.setWiredDeviceConnectionState(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_setBluetoothA2dpDeviceConnectionState:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _result = this.setBluetoothA2dpDeviceConnectionState(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_startWatchingRoutes:
{
data.enforceInterface(DESCRIPTOR);
android.media.IAudioRoutesObserver _arg0;
_arg0 = android.media.IAudioRoutesObserver.Stub.asInterface(data.readStrongBinder());
android.media.AudioRoutesInfo _result = this.startWatchingRoutes(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements android.media.IAudioService
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
public void adjustVolume(int direction, int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(direction);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_adjustVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void adjustLocalOrRemoteStreamVolume(int streamType, int direction) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
_data.writeInt(direction);
mRemote.transact(Stub.TRANSACTION_adjustLocalOrRemoteStreamVolume, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(direction);
_data.writeInt(suggestedStreamType);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_adjustSuggestedStreamVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void adjustStreamVolume(int streamType, int direction, int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
_data.writeInt(direction);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_adjustStreamVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void adjustMasterVolume(int direction, int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(direction);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_adjustMasterVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setStreamVolume(int streamType, int index, int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
_data.writeInt(index);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_setStreamVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setRemoteStreamVolume(int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_setRemoteStreamVolume, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void setMasterVolume(int index, int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_setMasterVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setStreamSolo(int streamType, boolean state, android.os.IBinder cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
_data.writeInt(((state)?(1):(0)));
_data.writeStrongBinder(cb);
mRemote.transact(Stub.TRANSACTION_setStreamSolo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setStreamMute(int streamType, boolean state, android.os.IBinder cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
_data.writeInt(((state)?(1):(0)));
_data.writeStrongBinder(cb);
mRemote.transact(Stub.TRANSACTION_setStreamMute, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean isStreamMute(int streamType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
mRemote.transact(Stub.TRANSACTION_isStreamMute, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setMasterMute(boolean state, int flags, android.os.IBinder cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((state)?(1):(0)));
_data.writeInt(flags);
_data.writeStrongBinder(cb);
mRemote.transact(Stub.TRANSACTION_setMasterMute, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean isMasterMute() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isMasterMute, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getStreamVolume(int streamType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
mRemote.transact(Stub.TRANSACTION_getStreamVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMasterVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMasterVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getStreamMaxVolume(int streamType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
mRemote.transact(Stub.TRANSACTION_getStreamMaxVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMasterMaxVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMasterMaxVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getLastAudibleStreamVolume(int streamType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
mRemote.transact(Stub.TRANSACTION_getLastAudibleStreamVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getLastAudibleMasterVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastAudibleMasterVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setRingerMode(int ringerMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(ringerMode);
mRemote.transact(Stub.TRANSACTION_setRingerMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int getRingerMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRingerMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setStreamSpeed(int streamType, int speed) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
_data.writeInt(speed);
mRemote.transact(Stub.TRANSACTION_setStreamSpeed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int getStreamSpeed(int streamType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
mRemote.transact(Stub.TRANSACTION_getStreamSpeed, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setVibrateSetting(int vibrateType, int vibrateSetting) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(vibrateType);
_data.writeInt(vibrateSetting);
mRemote.transact(Stub.TRANSACTION_setVibrateSetting, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int getVibrateSetting(int vibrateType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(vibrateType);
mRemote.transact(Stub.TRANSACTION_getVibrateSetting, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean shouldVibrate(int vibrateType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(vibrateType);
mRemote.transact(Stub.TRANSACTION_shouldVibrate, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setMode(int mode, android.os.IBinder cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
_data.writeStrongBinder(cb);
mRemote.transact(Stub.TRANSACTION_setMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int getMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void playSoundEffect(int effectType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(effectType);
mRemote.transact(Stub.TRANSACTION_playSoundEffect, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void playSoundEffectVolume(int effectType, float volume) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(effectType);
_data.writeFloat(volume);
mRemote.transact(Stub.TRANSACTION_playSoundEffectVolume, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public boolean loadSoundEffects() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_loadSoundEffects, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void unloadSoundEffects() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unloadSoundEffects, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void reloadAudioSettings() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_reloadAudioSettings, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void setSpeakerphoneOn(boolean on) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((on)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setSpeakerphoneOn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean isSpeakerphoneOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSpeakerphoneOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setBluetoothScoOn(boolean on) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((on)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setBluetoothScoOn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean isBluetoothScoOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isBluetoothScoOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setBluetoothA2dpOn(boolean on) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((on)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setBluetoothA2dpOn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean isBluetoothA2dpOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isBluetoothA2dpOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int requestAudioFocus(int mainStreamType, int durationHint, android.os.IBinder cb, android.media.IAudioFocusDispatcher l, java.lang.String clientId, java.lang.String callingPackageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mainStreamType);
_data.writeInt(durationHint);
_data.writeStrongBinder(cb);
_data.writeStrongBinder((((l!=null))?(l.asBinder()):(null)));
_data.writeString(clientId);
_data.writeString(callingPackageName);
mRemote.transact(Stub.TRANSACTION_requestAudioFocus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int abandonAudioFocus(android.media.IAudioFocusDispatcher l, java.lang.String clientId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((l!=null))?(l.asBinder()):(null)));
_data.writeString(clientId);
mRemote.transact(Stub.TRANSACTION_abandonAudioFocus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void unregisterAudioFocusClient(java.lang.String clientId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(clientId);
mRemote.transact(Stub.TRANSACTION_unregisterAudioFocusClient, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void dispatchMediaKeyEvent(android.view.KeyEvent keyEvent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((keyEvent!=null)) {
_data.writeInt(1);
keyEvent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_dispatchMediaKeyEvent, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void dispatchMediaKeyEventUnderWakelock(android.view.KeyEvent keyEvent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((keyEvent!=null)) {
_data.writeInt(1);
keyEvent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_dispatchMediaKeyEventUnderWakelock, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void registerMediaButtonIntent(android.app.PendingIntent pi, android.content.ComponentName c) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((pi!=null)) {
_data.writeInt(1);
pi.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((c!=null)) {
_data.writeInt(1);
c.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_registerMediaButtonIntent, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void unregisterMediaButtonIntent(android.app.PendingIntent pi, android.content.ComponentName c) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((pi!=null)) {
_data.writeInt(1);
pi.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((c!=null)) {
_data.writeInt(1);
c.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_unregisterMediaButtonIntent, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void registerMediaButtonEventReceiverForCalls(android.content.ComponentName c) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((c!=null)) {
_data.writeInt(1);
c.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_registerMediaButtonEventReceiverForCalls, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void unregisterMediaButtonEventReceiverForCalls() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unregisterMediaButtonEventReceiverForCalls, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public int registerRemoteControlClient(android.app.PendingIntent mediaIntent, android.media.IRemoteControlClient rcClient, java.lang.String callingPackageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((mediaIntent!=null)) {
_data.writeInt(1);
mediaIntent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((rcClient!=null))?(rcClient.asBinder()):(null)));
_data.writeString(callingPackageName);
mRemote.transact(Stub.TRANSACTION_registerRemoteControlClient, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void unregisterRemoteControlClient(android.app.PendingIntent mediaIntent, android.media.IRemoteControlClient rcClient) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((mediaIntent!=null)) {
_data.writeInt(1);
mediaIntent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((rcClient!=null))?(rcClient.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterRemoteControlClient, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void registerRemoteControlDisplay(android.media.IRemoteControlDisplay rcd) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((rcd!=null))?(rcd.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerRemoteControlDisplay, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void unregisterRemoteControlDisplay(android.media.IRemoteControlDisplay rcd) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((rcd!=null))?(rcd.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterRemoteControlDisplay, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void remoteControlDisplayUsesBitmapSize(android.media.IRemoteControlDisplay rcd, int w, int h) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((rcd!=null))?(rcd.asBinder()):(null)));
_data.writeInt(w);
_data.writeInt(h);
mRemote.transact(Stub.TRANSACTION_remoteControlDisplayUsesBitmapSize, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void setPlaybackInfoForRcc(int rccId, int what, int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(rccId);
_data.writeInt(what);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_setPlaybackInfoForRcc, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public int getRemoteStreamMaxVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRemoteStreamMaxVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getRemoteStreamVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRemoteStreamVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void registerRemoteVolumeObserverForRcc(int rccId, android.media.IRemoteVolumeObserver rvo) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(rccId);
_data.writeStrongBinder((((rvo!=null))?(rvo.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerRemoteVolumeObserverForRcc, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void startBluetoothSco(android.os.IBinder cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(cb);
mRemote.transact(Stub.TRANSACTION_startBluetoothSco, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void stopBluetoothSco(android.os.IBinder cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(cb);
mRemote.transact(Stub.TRANSACTION_stopBluetoothSco, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void forceVolumeControlStream(int streamType, android.os.IBinder cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
_data.writeStrongBinder(cb);
mRemote.transact(Stub.TRANSACTION_forceVolumeControlStream, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setRingtonePlayer(android.media.IRingtonePlayer player) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((player!=null))?(player.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setRingtonePlayer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public android.media.IRingtonePlayer getRingtonePlayer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.media.IRingtonePlayer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRingtonePlayer, _data, _reply, 0);
_reply.readException();
_result = android.media.IRingtonePlayer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMasterStreamType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMasterStreamType, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setWiredDeviceConnectionState(int device, int state, java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(device);
_data.writeInt(state);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_setWiredDeviceConnectionState, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int setBluetoothA2dpDeviceConnectionState(android.bluetooth.BluetoothDevice device, int state) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(state);
mRemote.transact(Stub.TRANSACTION_setBluetoothA2dpDeviceConnectionState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public android.media.AudioRoutesInfo startWatchingRoutes(android.media.IAudioRoutesObserver observer) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.media.AudioRoutesInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((observer!=null))?(observer.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_startWatchingRoutes, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.media.AudioRoutesInfo.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_adjustVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_adjustLocalOrRemoteStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_adjustSuggestedStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_adjustStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_adjustMasterVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setRemoteStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setMasterVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_setStreamSolo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setStreamMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_isStreamMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_setMasterMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_isMasterMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getMasterVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getStreamMaxVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getMasterMaxVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_getLastAudibleStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getLastAudibleMasterVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_setRingerMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_getRingerMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setStreamSpeed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getStreamSpeed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_setVibrateSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_getVibrateSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_shouldVibrate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_setMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_getMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_playSoundEffect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_playSoundEffectVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_loadSoundEffects = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_unloadSoundEffects = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_reloadAudioSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_setSpeakerphoneOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_isSpeakerphoneOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_setBluetoothScoOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_isBluetoothScoOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_setBluetoothA2dpOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_isBluetoothA2dpOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_requestAudioFocus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_abandonAudioFocus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
static final int TRANSACTION_unregisterAudioFocusClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
static final int TRANSACTION_dispatchMediaKeyEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
static final int TRANSACTION_dispatchMediaKeyEventUnderWakelock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
static final int TRANSACTION_registerMediaButtonIntent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
static final int TRANSACTION_unregisterMediaButtonIntent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
static final int TRANSACTION_registerMediaButtonEventReceiverForCalls = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
static final int TRANSACTION_unregisterMediaButtonEventReceiverForCalls = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
static final int TRANSACTION_registerRemoteControlClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 48);
static final int TRANSACTION_unregisterRemoteControlClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 49);
static final int TRANSACTION_registerRemoteControlDisplay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 50);
static final int TRANSACTION_unregisterRemoteControlDisplay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 51);
static final int TRANSACTION_remoteControlDisplayUsesBitmapSize = (android.os.IBinder.FIRST_CALL_TRANSACTION + 52);
static final int TRANSACTION_setPlaybackInfoForRcc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 53);
static final int TRANSACTION_getRemoteStreamMaxVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 54);
static final int TRANSACTION_getRemoteStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 55);
static final int TRANSACTION_registerRemoteVolumeObserverForRcc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 56);
static final int TRANSACTION_startBluetoothSco = (android.os.IBinder.FIRST_CALL_TRANSACTION + 57);
static final int TRANSACTION_stopBluetoothSco = (android.os.IBinder.FIRST_CALL_TRANSACTION + 58);
static final int TRANSACTION_forceVolumeControlStream = (android.os.IBinder.FIRST_CALL_TRANSACTION + 59);
static final int TRANSACTION_setRingtonePlayer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 60);
static final int TRANSACTION_getRingtonePlayer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 61);
static final int TRANSACTION_getMasterStreamType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 62);
static final int TRANSACTION_setWiredDeviceConnectionState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 63);
static final int TRANSACTION_setBluetoothA2dpDeviceConnectionState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 64);
static final int TRANSACTION_startWatchingRoutes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 65);
}
public void adjustVolume(int direction, int flags) throws android.os.RemoteException;
public void adjustLocalOrRemoteStreamVolume(int streamType, int direction) throws android.os.RemoteException;
public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) throws android.os.RemoteException;
public void adjustStreamVolume(int streamType, int direction, int flags) throws android.os.RemoteException;
public void adjustMasterVolume(int direction, int flags) throws android.os.RemoteException;
public void setStreamVolume(int streamType, int index, int flags) throws android.os.RemoteException;
public void setRemoteStreamVolume(int index) throws android.os.RemoteException;
public void setMasterVolume(int index, int flags) throws android.os.RemoteException;
public void setStreamSolo(int streamType, boolean state, android.os.IBinder cb) throws android.os.RemoteException;
public void setStreamMute(int streamType, boolean state, android.os.IBinder cb) throws android.os.RemoteException;
public boolean isStreamMute(int streamType) throws android.os.RemoteException;
public void setMasterMute(boolean state, int flags, android.os.IBinder cb) throws android.os.RemoteException;
public boolean isMasterMute() throws android.os.RemoteException;
public int getStreamVolume(int streamType) throws android.os.RemoteException;
public int getMasterVolume() throws android.os.RemoteException;
public int getStreamMaxVolume(int streamType) throws android.os.RemoteException;
public int getMasterMaxVolume() throws android.os.RemoteException;
public int getLastAudibleStreamVolume(int streamType) throws android.os.RemoteException;
public int getLastAudibleMasterVolume() throws android.os.RemoteException;
public void setRingerMode(int ringerMode) throws android.os.RemoteException;
public int getRingerMode() throws android.os.RemoteException;
public void setStreamSpeed(int streamType, int speed) throws android.os.RemoteException;
public int getStreamSpeed(int streamType) throws android.os.RemoteException;
public void setVibrateSetting(int vibrateType, int vibrateSetting) throws android.os.RemoteException;
public int getVibrateSetting(int vibrateType) throws android.os.RemoteException;
public boolean shouldVibrate(int vibrateType) throws android.os.RemoteException;
public void setMode(int mode, android.os.IBinder cb) throws android.os.RemoteException;
public int getMode() throws android.os.RemoteException;
public void playSoundEffect(int effectType) throws android.os.RemoteException;
public void playSoundEffectVolume(int effectType, float volume) throws android.os.RemoteException;
public boolean loadSoundEffects() throws android.os.RemoteException;
public void unloadSoundEffects() throws android.os.RemoteException;
public void reloadAudioSettings() throws android.os.RemoteException;
public void setSpeakerphoneOn(boolean on) throws android.os.RemoteException;
public boolean isSpeakerphoneOn() throws android.os.RemoteException;
public void setBluetoothScoOn(boolean on) throws android.os.RemoteException;
public boolean isBluetoothScoOn() throws android.os.RemoteException;
public void setBluetoothA2dpOn(boolean on) throws android.os.RemoteException;
public boolean isBluetoothA2dpOn() throws android.os.RemoteException;
public int requestAudioFocus(int mainStreamType, int durationHint, android.os.IBinder cb, android.media.IAudioFocusDispatcher l, java.lang.String clientId, java.lang.String callingPackageName) throws android.os.RemoteException;
public int abandonAudioFocus(android.media.IAudioFocusDispatcher l, java.lang.String clientId) throws android.os.RemoteException;
public void unregisterAudioFocusClient(java.lang.String clientId) throws android.os.RemoteException;
public void dispatchMediaKeyEvent(android.view.KeyEvent keyEvent) throws android.os.RemoteException;
public void dispatchMediaKeyEventUnderWakelock(android.view.KeyEvent keyEvent) throws android.os.RemoteException;
public void registerMediaButtonIntent(android.app.PendingIntent pi, android.content.ComponentName c) throws android.os.RemoteException;
public void unregisterMediaButtonIntent(android.app.PendingIntent pi, android.content.ComponentName c) throws android.os.RemoteException;
public void registerMediaButtonEventReceiverForCalls(android.content.ComponentName c) throws android.os.RemoteException;
public void unregisterMediaButtonEventReceiverForCalls() throws android.os.RemoteException;
public int registerRemoteControlClient(android.app.PendingIntent mediaIntent, android.media.IRemoteControlClient rcClient, java.lang.String callingPackageName) throws android.os.RemoteException;
public void unregisterRemoteControlClient(android.app.PendingIntent mediaIntent, android.media.IRemoteControlClient rcClient) throws android.os.RemoteException;
public void registerRemoteControlDisplay(android.media.IRemoteControlDisplay rcd) throws android.os.RemoteException;
public void unregisterRemoteControlDisplay(android.media.IRemoteControlDisplay rcd) throws android.os.RemoteException;
public void remoteControlDisplayUsesBitmapSize(android.media.IRemoteControlDisplay rcd, int w, int h) throws android.os.RemoteException;
public void setPlaybackInfoForRcc(int rccId, int what, int value) throws android.os.RemoteException;
public int getRemoteStreamMaxVolume() throws android.os.RemoteException;
public int getRemoteStreamVolume() throws android.os.RemoteException;
public void registerRemoteVolumeObserverForRcc(int rccId, android.media.IRemoteVolumeObserver rvo) throws android.os.RemoteException;
public void startBluetoothSco(android.os.IBinder cb) throws android.os.RemoteException;
public void stopBluetoothSco(android.os.IBinder cb) throws android.os.RemoteException;
public void forceVolumeControlStream(int streamType, android.os.IBinder cb) throws android.os.RemoteException;
public void setRingtonePlayer(android.media.IRingtonePlayer player) throws android.os.RemoteException;
public android.media.IRingtonePlayer getRingtonePlayer() throws android.os.RemoteException;
public int getMasterStreamType() throws android.os.RemoteException;
public void setWiredDeviceConnectionState(int device, int state, java.lang.String name) throws android.os.RemoteException;
public int setBluetoothA2dpDeviceConnectionState(android.bluetooth.BluetoothDevice device, int state) throws android.os.RemoteException;
public android.media.AudioRoutesInfo startWatchingRoutes(android.media.IAudioRoutesObserver observer) throws android.os.RemoteException;
}
