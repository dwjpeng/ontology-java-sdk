package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.crypto.SignatureScheme;
import com.github.ontio.sdk.exception.SDKException;
import com.github.ontio.smartcontract.neovm.abi.BuildParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PunicaInitDemo {

    private static OntSdk ontSdk = null;

    public static String contractCode = "013fc56b6a00527ac46a51527ac46a00c30568656c6c6f9c6424006a51c3c0519e640700006c7566616a51c300c36a52527ac46a52c365fe066c7566616a00c3097465737448656c6c6f9c646c006a51c3c0559e640700006c7566616a51c300c36a53527ac46a51c351c36a54527ac46a51c352c36a55527ac46a51c353c36a56527ac46a51c354c36a57527ac46a53c36a54c36a55c36a56c36a57c354795179567275517275537952795572755272756500066c7566616a00c30b746573744e756d4c6973749c6424006a51c3c0519e640700006c7566616a51c300c36a58527ac46a58c36592056c7566616a00c311746573744e756d4c697374416e645374729c6451006a51c351c176c9681553797374656d2e52756e74696d652e4e6f74696679616a51c3c0529e640700006c7566616a51c300c36a58527ac46a51c351c36a56527ac46a58c36a56c37c65cc046c7566616a00c311746573745374724c697374416e645374729c6432006a51c3c0529e640700006c7566616a51c300c36a59527ac46a51c351c36a56527ac46a59c36a56c37c6525046c7566616a00c317746573744279746541727261794c697374416e645374729c6432006a51c3c0529e640700006c7566616a51c300c36a5a527ac46a51c351c36a52527ac46a5ac36a52c37c6572036c7566616a00c30e746573745374727563744c6973749c6431006a51c3681553797374656d2e52756e74696d652e4e6f74696679616a51c300c36a5b527ac46a5bc365f1026c7566616a00c314746573745374727563744c697374416e645374729c6432006a51c3c0529e640700006c7566616a51c300c36a5b527ac46a51c351c36a56527ac46a5bc36a56c37c6544026c7566616a00c307746573744d61709c6416006a51c300c36a52527ac46a52c36583016c7566616a00c30a746573744765744d61709c6424006a51c3c0519e640700006c7566616a51c300c36a5c527ac46a5cc365d9006c7566616a00c30d7472616e736665724d756c74699c640c006a51c3650b006c756661006c756659c56b6a00527ac4006a52527ac46a00c3c06a53527ac4616a52c36a53c39f6473006a00c36a52c3c36a51527ac46a52c351936a52527ac46a51c3c0539e6420001b7472616e736665724d756c746920706172616d73206572726f722ef0616a51c300c36a51c351c36a51c352c35272652900009c64a2ff157472616e736665724d756c7469206661696c65642ef06288ff616161516c756656c56b6a00527ac46a51527ac46a52527ac4516c756656c56b6a00527ac4681953797374656d2e53746f726167652e476574436f6e7465787461076d61705f6b65797c681253797374656d2e53746f726167652e476574616a51527ac46a51c3681a53797374656d2e52756e74696d652e446573657269616c697a65616a52527ac46a52c36a00c3c36c756658c56b6a00527ac46a00c36a51527ac46a51c3681853797374656d2e52756e74696d652e53657269616c697a65616a52527ac46a00c36a51c352c176c9681553797374656d2e52756e74696d652e4e6f7469667961681953797374656d2e53746f726167652e476574436f6e7465787461076d61705f6b65796a52c35272681253797374656d2e53746f726167652e507574616a51c3036b6579c36c756659c56b6a00527ac46a51527ac414746573745374727563744c697374416e645374726a00c36a51c353c176c9681553797374656d2e52756e74696d652e4e6f746966796100c176c96a52527ac46a52c36a00c3c86a52c36a51c3c86a52c36c756655c56b6a00527ac40e746573745374727563744c6973746a00c352c176c9681553797374656d2e52756e74696d652e4e6f74696679616a00c36c756659c56b6a00527ac46a51527ac417746573744279746541727261794c697374416e645374726a00c36a51c353c176c9681553797374656d2e52756e74696d652e4e6f746966796100c176c96a52527ac46a52c36a00c3c86a52c36a51c3c86a52c36c756659c56b6a00527ac46a51527ac411746573745374724c697374416e645374726a00c36a51c353c176c9681553797374656d2e52756e74696d652e4e6f746966796100c176c96a52527ac46a52c36a00c3c86a52c36a51c3c86a52c36c756659c56b6a00527ac46a51527ac411746573744e756d4c697374416e645374726a00c36a51c353c176c9681553797374656d2e52756e74696d652e4e6f746966796100c176c96a52527ac46a52c36a00c3c86a52c36a51c3c86a52c36c756655c56b6a00527ac40b746573744e756d4c6973746a00c352c176c9681553797374656d2e52756e74696d652e4e6f74696679616a00c36c75665fc56b6a00527ac46a51527ac46a52527ac46a53527ac46a54527ac4097465737448656c6c6f6a00c36a51c36a52c36a53c36a54c356c176c9681553797374656d2e52756e74696d652e4e6f746966796100c176c96a55527ac46a55c36a00c3c86a55c36a51c3c86a55c36a52c3c86a55c36a53c3c86a55c36a54c3c86a55c36c756654c56b6a00527ac46a00c36c7566";

    private PunicaInitDemo(){
    }


    public static void main(String[] args2) {
        try{
            ontSdk = getOntSdk();
            Account account = new Account(Helper.hexToBytes("274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b"), SignatureScheme.SHA256WITHECDSA);
            Account account2 = new Account(Helper.hexToBytes("67ae8a3731709d8c820c03b200b9552ec61e6634cbcaf8a6a1f9d8f9f0f608"), SignatureScheme.SHA256WITHECDSA);

            // deployment
            if(false){
                System.out.println("Test Function deployContract start -------");
                System.out.println("ContractAddress:" + Address.AddressFromVmCode(contractCode).toHexString());

                ontSdk.vm().setCodeAddress(Address.AddressFromVmCode(contractCode).toHexString());
                Transaction tx = ontSdk.vm().makeDeployCodeTransaction(contractCode, true, "name",
                        "v1.0", "author", "email", "desp", account.getAddressU160().toBase58(),30000000L,0);
                ontSdk.signTx(tx, new Account[][]{{account}});
                String txHex = Helper.toHexString(tx.toArray());

                System.out.println(txHex);
                Object result = ontSdk.getConnect().syncSendRawTransaction(txHex);
                System.out.println(result);
                System.out.println();
                System.exit(0);
            }

            // hello
            if(false){
                System.out.println();
                System.out.println("1. Test Function hello start -------");
                List paramList = new ArrayList<>();
                paramList.add("hello".getBytes());

                List args = new ArrayList();
                args.add("helloWorld");

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);

                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                System.out.println("hello return is:  " + new String(Helper.hexToBytes(JSON.parseObject(result).getString("Result"))));
                System.out.println();
            }

            // testHello
            if(false){
                System.out.println("2. Test Function testHello start -------");
                List paramList = new ArrayList<>();
                paramList.add("testHello".getBytes());

                List args = new ArrayList();
                args.add(true);
                args.add(100);
                args.add("test".getBytes());
                args.add("test");
                args.add(account.getAddressU160().toArray());

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);

                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                List<String > resultList = JSON.parseObject(JSON.parseObject(result).getString("Result"), List.class);
                System.out.println("testHello return size is:  " + resultList.size());
                System.out.println();
            }

            // testNumList
            if(false){
                System.out.println("3. Test Function testNumList start -------");
                List paramList = new ArrayList<>();
                paramList.add("testNumList".getBytes());

                List args = new ArrayList();
                List list = new ArrayList();
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                list.add(5);
                args.add(list);

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);


                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                List<String > resultList = JSON.parseObject(JSON.parseObject(result).getString("Result"), List.class);
                System.out.println("testNumList return size is:  " + resultList.size());
                System.out.println("testNumList return is:  " + Integer.valueOf(resultList.get(0)));
                System.out.println();
            }

            // testNumListAndStr
            if(false){
                System.out.println("4. Test Function testNumListAndStr start -------");
                List paramList = new ArrayList<>();
                paramList.add("testNumListAndStr".getBytes());

                List args = new ArrayList();
                List list = new ArrayList();
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                list.add(5);
                args.add(list);
                args.add("test");

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);


                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                List<String > resultList = JSON.parseObject(JSON.parseObject(result).getString("Result"), List.class);
                System.out.println("testNumListAndStr return size is:  " + resultList.size());
                System.out.println("testNumListAndStr Str return is:  " + new String(Helper.hexToBytes(resultList.get(1))));
                System.out.println();
            }

            // testStrListAndStr
            if(false){
                System.out.println("5. Test Function testStrListAndStr start -------");
                List paramList = new ArrayList<>();
                paramList.add("testStrListAndStr".getBytes());

                List args = new ArrayList();
                List list = new ArrayList();
                list.add("hello");
                list.add("world");
                args.add(list);
                args.add("test");

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);

                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                List<String > resultList = JSON.parseObject(JSON.parseObject(result).getString("Result"), List.class);

                System.out.println("testStrListAndStr return size is:  " + resultList.size());
                System.out.println("testStrListAndStr first param return is:  " + new String(Helper.hexToBytes((String) JSON.parseObject(JSONObject.toJSON(resultList.get(0)).toString(), List.class).get(0))));
                System.out.println();
            }

            // testByteArrayListAndStr
            if(false){
                System.out.println("6. Test Function testByteArrayListAndStr start -------");
                List paramList = new ArrayList<>();
                paramList.add("testByteArrayListAndStr".getBytes());

                List args = new ArrayList();
                List list = new ArrayList();
                list.add("hello".getBytes());
                list.add("world".getBytes());
                args.add(list);
                args.add("test");

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);

                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                List<String > resultList = JSON.parseObject(JSON.parseObject(result).getString("Result"), List.class);
                System.out.println("testByteArrayListAndStr return size is:  " + resultList.size());
                System.out.println("testByteArrayListAndStr first param return is:  " + new String(Helper.hexToBytes((String) JSON.parseObject(JSONObject.toJSON(resultList.get(0)).toString(), List.class).get(0))));
                System.out.println();
            }

            // testStructList
            if(false){
                System.out.println("7. Test Function testStructList start -------");
                List paramList = new ArrayList<>();
                paramList.add("testStructList".getBytes());

                List args = new ArrayList();
                List list = new ArrayList();
                List structList = new ArrayList();
                List structList2 = new ArrayList();
                structList.add("hello");
                structList.add(1);
                structList2.add("hello2");
                structList2.add(2);
                list.add(structList);
                list.add(structList2);
                args.add(list);

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);

                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                List<String > resultList = JSON.parseObject(JSON.parseObject(result).getString("Result"), List.class);

                System.out.println("testStructList return size is:  " + resultList.size());
                System.out.println();
            }

            // testStructListAndStr
            if(false){
                System.out.println("8. Test Function testStructListAndStr start -------");
                List paramList = new ArrayList<>();
                paramList.add("testStructListAndStr".getBytes());

                List args = new ArrayList();
                List list = new ArrayList();
                List structList = new ArrayList();
                List structList2 = new ArrayList();
                structList.add("hello");
                structList.add(1);
                structList2.add("hello2");
                structList2.add(2);
                list.add(structList);
                list.add(structList2);
                args.add(list);
                args.add("test");

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);

                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                List<String > resultList = JSON.parseObject(JSON.parseObject(result).getString("Result"), List.class);
                System.out.println("testStructListAndStr return size is:  " + resultList.size());
                System.out.println();
            }

            // testMap
            if(true){
                System.out.println("9. Test Function testMap start -------");
                List paramList = new ArrayList<>();
                paramList.add("testMap".getBytes());

                List args = new ArrayList();
                Map map = new HashMap<>();
                Map map2 = new HashMap<>();
                map2.put("key","hello2");
//                map2.put("key2","hello".getBytes());
//                map2.put("key3",Long.valueOf(100));
//                map2.put("key4",true);
                map.put("key",map2);
                args.add(map2);

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);
                String preResult = invokeContract(params, account, 20000, 500,true);
                System.out.println(preResult);
                String result = invokeContract(params, account, 20000, 500,false);
                Thread.sleep(6000);
                System.out.println(result);
                System.out.println("testMap return is:  " + result);
                System.out.println();
            }

            // testGetMap
            if(true){
                System.out.println("10. Test Function testGetMap start -------");
                List paramList = new ArrayList<>();
                paramList.add("testGetMap".getBytes());

                List args = new ArrayList();
                args.add("key");

                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);

                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                System.out.println();
            }
            // transferMulti
            if(false){
                System.out.println("11. Test Function transferMulti start -------");
                List paramList = new ArrayList<>();
                paramList.add("transferMulti".getBytes());

                List args = new ArrayList();
                List state = new ArrayList();
                List state2 = new ArrayList();
                state.add(account.getAddressU160().toArray());
                state.add(account.getAddressU160().toArray());
                state.add(Long.valueOf(10));
                state2.add(account.getAddressU160().toArray());
                state2.add(account.getAddressU160().toArray());
                state2.add(Long.valueOf(10));
                args.add(state);
                args.add(state2);
                paramList.add(args);
                byte[] params = BuildParams.createCodeParamsScript(paramList);

                String result = invokeContract(params, account, 20000, 500,true);
                System.out.println(result);
                System.out.println();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String invokeContract(byte[] params, Account payerAcct, long gaslimit, long gasprice, boolean preExec) throws Exception{
        if(payerAcct == null){
            throw new SDKException("params should not be null");
        }
        if(gaslimit < 0 || gasprice< 0){
            throw new SDKException("gaslimit or gasprice should not be less than 0");
        }

        Transaction tx = ontSdk.vm().makeInvokeCodeTransaction(Helper.reverse(ontSdk.neovm().oep4().getContractAddress()),null,params,payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);

        ontSdk.addSign(tx, payerAcct);
//  MultiSign
//        Account account = new Account(Helper.hexToBytes("274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b"),SignatureScheme.SHA256WITHECDSA);
//        Account account2 = new Account(Helper.hexToBytes("67ae8a3731709d8c820c03b200b9552ec61e6634cbcaf8a6a1f9d8f9f0f608"),SignatureScheme.SHA256WITHECDSA);
//        ontSdk.addMultiSign(tx,2,new byte[][]{account.serializePublicKey(),account2.serializePublicKey()},account);
//        ontSdk.addMultiSign(tx,2,new byte[][]{account.serializePublicKey(),account2.serializePublicKey()},account2);
        Object result = null;
        if(preExec) {
            result = ontSdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
        }else {
            result = ontSdk.getConnect().sendRawTransaction(tx.toHexString());
            return tx.hash().toString();
        }

        return result.toString();
    }


    public static OntSdk getOntSdk() throws Exception{
        String ip = "http://127.0.0.1";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" +  "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());
        wm.neovm().oep4().setContractAddress("3dc4af5ecaad646e046c0b318a483d759a7a1ada");             //msg 3dc4af5ecaad646e046c0b318a483d759a7a1ada    de  0889bfd017f8ef75922e0805d4ec6fe6fff3c00d
        wm.openWalletFile("wallet.json");

        return wm;
    }
}