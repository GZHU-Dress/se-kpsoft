package protocol;

import log.Log;

import java.util.LinkedList;

/**
 * 该类用于文本信息和协议体的编解码
 *
 * 该类结合基本结构体{@link ProtocolStruct}，里面只提供四个语义模块
 * 该类的两个方法实现了对四个语义模块的编解码
 * 具体的模块内的东西由子类去实现
 *
 * Created by zyvis on 2017/11/29.
 */
public class EncoderImpl implements Encoder{
    /**
     *  编码器
     *  完成对协议体的编码，会调用结构体本身的方法进行转义
     *  剩下的按照协议的标准生成，拥有四个协议的语义模块
     *
     * @param protocolStruct    结构体
     * @return                  编码结果
     * @throws EncoderException 编码异常
     */
    @Override
    public String encode(ProtocolStruct protocolStruct) throws EncoderException {
        Log.d(protocolStruct.toString());
        protocolStruct.escapeAll();
        String tmp="";
        tmp+= ProtocolStruct.getMarkupHead(ProtocolStruct.protocolRound);
        tmp+= ProtocolStruct.getMarkupHead(ProtocolStruct.headerRound);
        if(protocolStruct.getHeader()==null|| protocolStruct.getHeader().length()<=ProtocolStruct.headerAtLeastLength)
            throw new EncoderException("header is null or header is so short",protocolStruct);
        tmp+= protocolStruct.getHeader();
        tmp+= ProtocolStruct.getMarkupTail(ProtocolStruct.headerRound);
        tmp+= ProtocolStruct.getMarkupHead(ProtocolStruct.deviceRound);
        tmp+= protocolStruct.getDevice();
        tmp+= ProtocolStruct.getMarkupTail(ProtocolStruct.deviceRound);
        tmp+= ProtocolStruct.getMarkupHead(ProtocolStruct.instructionRound);
        tmp+= protocolStruct.getInstruction();
        tmp+= ProtocolStruct.getMarkupTail(ProtocolStruct.instructionRound);
        tmp+= ProtocolStruct.getMarkupHead(ProtocolStruct.dataRound);
        String[] tmps= protocolStruct.getData();
        for (String s:tmps){
            tmp+= ProtocolStruct.getMarkupHead(ProtocolStruct.dataMiniRound);
            tmp+=s;
            tmp+= ProtocolStruct.getMarkupTail(ProtocolStruct.dataMiniRound);
        }
        tmp+= ProtocolStruct.getMarkupTail(ProtocolStruct.dataRound);
        tmp+= ProtocolStruct.getMarkupTail(ProtocolStruct.protocolRound);
        Log.d(tmp);
        return tmp;
    }

    /**
     * 解码器
     * 根据文本的标记符号语言进行拆分，生成协议体对象
     * 根据协议要求只能有单独的语义协议块（四个语义块分别只能出现一次）
     * 如果多次出现会抛出协议体异常
     *
     * @param msg                   可以转化为协议体的文本
     * @return                      生成协议体对象
     * @throws EncoderException     编码错误
     */
    @Override
    public ProtocolStruct decode(String msg) throws EncoderException {
        String[] singleArray=new String[4];
        String[] tagArray=new String[]{
                ProtocolStruct.headerRound,
                ProtocolStruct.deviceRound,
                ProtocolStruct.instructionRound,
                ProtocolStruct.dataRound
        };
        String[] data;
        Log.d("origin msg : "+msg);
        String holy=isLengthEqualsOne(unpackMarkup(msg,ProtocolStruct.protocolRound),ProtocolStruct.protocolRound);
        Log.d("unpack "+ProtocolStruct.protocolRound+": "+holy);

        for (int i = 0; i < tagArray.length; i++) {
            singleArray[i]=isLengthEqualsOne(unpackMarkup(holy,tagArray[i]),tagArray[i]);
            Log.d("unpack "+tagArray[i]+": "+singleArray[i]);
        }

        data=unpackMarkup(singleArray[3],"");
        ProtocolStruct aps= new ProtocolStruct(singleArray[0],singleArray[1],singleArray[2],data);
        aps.pursueAll();
        Log.d(aps.toString());
        return aps;

    }

    /**
     * 该方法来配合{@link EncoderImpl#unpackMarkup(String, String)}
     * 检查该标记是不是整段文本中唯一的标记
     * 如果信息原文组的长度是一则说明只有一个对象（根据上面方法定义）
     * 为当前的协议版本需求
     *
     * 写这个方法主要是避免在{@link EncoderImpl#decode(String)}
     * 循环调用时候产生的代码重复
     *
     * @param msg               信息原文组
     * @param RoundTag          查询的
     * @return                  返回信息原文组
     * @throws EncoderException 抛出不是唯一标记的异常
     */
    private String isLengthEqualsOne(String[] msg,String RoundTag)throws EncoderException{
        if(msg.length!=1)
            throw new EncoderException("msg syntax is not in law : not the only <"+ RoundTag+"> and </"+ RoundTag+"> markup in msg");
        return msg[0];
    }

    /**
     * 这个方法来给标记符号解套，按照指定的tag完成解套返回
     * 如 <p>hello-world</p>outside<p>hello-world</p>
     * 返回 String["hello-world","hello-world"]
     *
     * @author      S
     * @param msg   携带标记符号的原文
     * @param tag   要解套的标记符号
     * @return      解套后的文本数组
     */
    public String[] unpackMarkup(String msg,String tag){
        int h,t,vis=0;
        LinkedList<String> list=new LinkedList<>();
        String head=ProtocolStruct.getMarkupHead(tag);
        String tail=ProtocolStruct.getMarkupTail(tag);
        while((vis=msg.indexOf(head, vis))!=-1){
            h=vis+head.length();
            if((vis=msg.indexOf(tail, vis))!=-1){
                t=vis;
                 list.add(msg.substring(h, t));
            }
            else {
                break;
            }
        }
        String[] rec=new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            rec[i]=list.get(i);
        }
        return rec;
    }
}
