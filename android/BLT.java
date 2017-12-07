package MsgMgr;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

public class BLT  {
	
	private final static String TAG = "Bluetooth";

	private Context mContext;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothDevice mBluetoothDevice;
	private BluetoothGatt mBluetoothGatt;
	/**
	 * 
	 * @param context
	 * 构造器
	 */
	public BLT(Context context) {
		this.mContext = context;
		BluetoothManager mBluetoothManager = (BluetoothManager)context
				.getSystemService(Context.BLUETOOTH_SERVICE);
		if (mBluetoothManager != null) {
			mBluetoothAdapter = mBluetoothManager.getAdapter();
			if (mBluetoothAdapter != null) {
				if (!mBluetoothAdapter.isEnabled()) {
					mBluetoothAdapter.enable(); // 打开蓝牙
				}
			}
		}
	}
	/**
	 * 
	 * @return Set<BluetoothDevice> 蓝牙设备集合
	 */
	public Set<BluetoothDevice> startLeScan() {
		if(mBluetoothAdapter==null)return null;
		return mBluetoothAdapter.getBondedDevices(); 
	}

	/**
	 * 
	 * @param BluetoothDevice  要连接的蓝牙设备
	 * @return
	 */
	public boolean connect(BluetoothDevice target) {
		
		mBluetoothDevice=target;
		mBluetoothGatt = mBluetoothDevice.connectGatt(mContext, false,mGattCallback); 
		// mGattCallback为回调接口

		if (mBluetoothGatt != null) {

			if (mBluetoothGatt.connect()) {
				Log.d(TAG, "Connect succeed.");
				return true;
			}
			else {
				Log.d(TAG, "Connect fail.");
				return false;
			}
		} 
		else {
			Log.d(TAG, "BluetoothGatt null.");
			return false;
		}
	}

	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,final int newState) {
			super.onConnectionStateChange(gatt, status, newState);
			switch (newState) {
			//已经连接
			case BluetoothGatt.STATE_CONNECTED:
				Log.i(TAG,"已连接");
				//该方法用于获取设备的服务，寻找服务
				//bluetoothGatt.discoverServices();
				break;
				//正在连接
			case BluetoothGatt.STATE_CONNECTING:
				Log.i(TAG,"正在连接");
				break;
				//连接断开
			case BluetoothGatt.STATE_DISCONNECTED:
				Log.i(TAG,"已断开");
				break;
				//正在断开
			case BluetoothGatt.STATE_DISCONNECTING:
				Log.i(TAG,"断开中");
				break;
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			super.onServicesDiscovered(gatt, status);
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			super.onCharacteristicRead(gatt, characteristic, status);
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			super.onCharacteristicChanged(gatt, characteristic);
		}

		
		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			super.onCharacteristicWrite(gatt, characteristic, status);
		}
	};
}

