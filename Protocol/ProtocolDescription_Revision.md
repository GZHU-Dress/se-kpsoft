對等通信協議

# 協議頭``<kp>``协议尾``</kp>``

- p之间由分号分隔。

## L1 header首部行，以h为标记

- p0 版本號：0.1開始
- p1 消息類型：
  - ACT动作信息：
    - 需要指明设备、操作和参数
  - STA状态信息：
    - ACK：確認請求
    - RFS：拒絕請求
  - DAT数据信息：
    - 数据集合

- 该语义模块要求最少包含两个字符，在编解码器里作为判断依据。

## L2 device设备行，以v为标记
- p0 設備屬：BLT(藍牙)、GPS、BWS(浏览器)等，指明信息的目标者
- [p1 时间戳]
- [p2 時間戳偏移]

## L3 instruction 指令行，以i为标记
- p0 此塊區為以後的擴展協議保留擴展空間

## L4 data数据行，以d为标记
（數據字段建議使用json），頭尾使用<>標籤分割


# sample1
浏览器获取蓝牙信息
```
<kp>
<h>0.1;ACT</h>
<v>BLT</v>
<i></i>
<d></d>
</kp>
```

后台反饋三段数据：
```
<kp>
<h>0.1;DAT</h>
<v>BLT</v>
<i></i>
<d>
    <>this is the data range1</>
    <>this is the data range2</>
    <>this is the data range3</>
</d>
</kp>
```

浏览器接收到最後的</kp>，表示信息全部收到，反饋ACK
```
<kp>
<h>0.1;ACK</h>
<v>BWS</v>
<i></i>
<d></d>
</kp>
```

后台拒绝访问：
```
<kp>
<h>0.1;RFS</h>
<v>BLT</v>
<i></i>
<d>exception occurred</d>
</kp>
```

# sample2
指令操作：
```
<kp>
<h>0.1;ACT</h>
<v>BLT</v>
<i>ison=isOn();canconnect=canConnect()</i>
<d></d>
</kp>
```

后台反馈（第二个方法没有找到的情况下）：
```
<kp>
<h>0.1;ACK</h>
<v>BLT</v>
<i></i>
<d>
    <>ison="true"</>
    <>canconnect=""</>
</d>
</kp>
```

传输过程中不带\n符号
即
- ``<kp><h>0.1;ACK</h><v>BLT</v><i></i><d><>ison="true"</><>canconnect=""</></d></kp>``
- ``<kp><h>0.1;ACT</h><v>BLT</v><i>ison=isOn();canconnect=canConnect();</i><d></d></kp>``