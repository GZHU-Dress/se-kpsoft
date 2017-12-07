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
	 * ������
	 */
	public BLT(Context context) {
		this.mContext = context;
		BluetoothManager mBluetoothManager = (BluetoothManager)context
				.getSystemService(Context.BLUETOOTH_SERVICE);
		if (mBluetoothManager != null) {
			mBluetoothAdapter = mBluetoothManager.getAdapter();
			if (mBluetoothAdapter != null) {
				if (!mBluetoothAdapter.isEnabled()) {
					mBluetoothAdapter.enable(); // ������
				}
			}
		}
	}
	/**
	 * 
	 * @return Set<BluetoothDevice> �����豸����
	 */
	public Set<BluetoothDevice> startLeScan() {
		if(mBluetoothAdapter==null)return null;
		return mBluetoothAdapter.getBondedDevices(); 
	}

	/**
	 * 
	 * @param BluetoothDevice  Ҫ���ӵ������豸
	 * @return
	 */
	public boolean connect(BluetoothDevice target) {
		
		mBluetoothDevice=target;
		mBluetoothGatt = mBluetoothDevice.connectGatt(mContext, false,mGattCallback); 
		// mGattCallbackΪ�ص��ӿ�

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
			//�Ѿ�����
			case BluetoothGatt.STATE_CONNECTED:
				Log.i(TAG,"������");
				//�÷������ڻ�ȡ�豸�ķ���Ѱ�ҷ���
				//bluetoothGatt.discoverServices();
				break;
				//��������
			case BluetoothGatt.STATE_CONNECTING:
				Log.i(TAG,"��������");
				break;
				//���ӶϿ�
			case BluetoothGatt.STATE_DISCONNECTED:
				Log.i(TAG,"�ѶϿ�");
				break;
				//���ڶϿ�
			case BluetoothGatt.STATE_DISCONNECTING:
				Log.i(TAG,"�Ͽ���");
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

