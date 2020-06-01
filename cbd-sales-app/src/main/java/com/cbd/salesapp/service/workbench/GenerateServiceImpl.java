package com.cbd.salesapp.service.workbench;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.GenerateService;
import com.cbd.cbdcommoninterface.response.salesapp.workbench.GenerateVO;

/**
 * @author: Monster
 * @date: 2020/5/31 13:33
 * @Description
 */
public class GenerateServiceImpl implements GenerateService {
    @Override
    public GenerateVO generateContract() {
        GenerateVO result = new GenerateVO();
        result.setText("1、人身伤害责任险／Bodily Injury Liability\n" +
                "\n" +
                "车祸你的过错造成的时候，如果买了这个选项，保险将赔付对方的身体伤害，比如对方的医疗费等，有的还有精神伤害，这个需要上法庭决定了,人身伤害责任险（Bodily Injury Liability），包括对方的医疗、康复、精神损失、殡葬、你的律师等费用，遇事后，加州法律规定的最低保额为每人$15,000美元，整宗事故保额最低为$30,000美元；虽然法律规定3万就行，但尽量买大额度。\n" +
                "\n" +
                "有些人无资产真穷，对方律师一般不会死缠，赢了也无物可抵押，论坛上甚至不少躲赔偿学位也不要了直接回国的例子。但想长期发展的都会在能力范围内选更大保险额度。避免因一次事故，影响未来生活。\n" +
                "\n" +
                "2、医疗费用保险／Medical Expenses or Personal Injury Protection\n" +
                "\n" +
                "驾驶者自己和车上乘客的医疗费用的保险。一般包括医疗费、丧葬费等。但是Medical Payments不包括误工费，和其他因身体伤害而产生的费用。而Personal Injury Protection所赔付的范围就比较大，包括误工费等其他费用。因为你的车上很可能有其他乘客，所以这项车险条款是有必要购买的。如果自己本身已经有了较好的医疗保险，可以不买，但如果车上还有其他乘客，最好买。\n" +
                "\n" +
                "举个例子，每年新生来了，老生带新生买菜，最好有这个险；又比如，你带邻居刚来的叔叔阿姨去中国店，也应该买这个险。总之，只要你会carpool，让其他人坐你的车，就最好买医疗费用保险。\n" +
                "\n" +
                "美国有12个州有no-fault auto insurance法律，也就是不管谁的责任，你的保险公司都要赔付你因人身伤害造成的损失：\n" +
                "\n" +
                "“Currently 12 states and Puerto Rico have no-fault auto insurance laws. Florida , Michigan , New Jersey, New York and Pennsylvania have verbal thresholds. The other seven states— Hawaii , Kansas , Kentucky , Massachusetts , Minnesota , North Dakota and Utah — use a monetary threshold.”\n" +
                "\n" +
                "所以如果你在这12个州，需要和保险公司了解一下具体条款。\n" +
                "\n" +
                "3、财产损失责任险／Property Damage Liability\n" +
                "\n" +
                "你是过错方，用来赔付对方的财产损失。此处的财产损失通常指对他人的车造成的损害，包括对方的车、房、篱笆等财产损失， 也包含你开车撞到电线杆、栅栏、建筑物等情况。绝大部分州都规定财产损失责任险必须购买。\n" +
                "\n" +
                "4、碰撞险／Collision\n" +
                "\n" +
                "购买碰撞险的时候，一般会让你选择自付额度（Deductible ），范围在$250到$1000之间。自付额，也叫免赔额，是指无论是因为撞上其它车辆，或电线杆、篱笆、房屋等物体造成被保车辆损毁，保险公司都会给付超过自付额的修车费用，如果车子完全报废，保险也会依该车当时市值作出赔偿，最高理赔金额为保额上限。这项保险实际是责任险对自身车辆的延伸及补充。也就是说，如果选择了250美元的的自付额，出事后，要先自付250刀，超过的部分才由保险公司负责。所以自付额越高，保费越低。有的保险有0自付，保费也更贵。\n" +
                "\n" +
                "5、综合意外险／Comprehensive\n" +
                "\n" +
                "保障任何不明对像或者天灾对车所造成的损失。包括被偷，火烧，水淹，树砸等等情况。 有自付额，一般会选500刀自付。这个理赔，不一定影响来年保费，如洪水，因概率低，也不是因驾驶技术引发。如果你车通过租或贷款方式购买，这个保险会强制要求购买。\n" +
                "\n" +
                "6、无保险或保险不足驾驶人保险／Uninsured and Underinsured Motorists Coverage：\n" +
                "\n" +
                "这项保险是保险你自己的。如果出事后对方肇事司机无保险，或者保险额度不够赔偿你的损失，或者对方逃逸，这一项就会赔钱给你。通常，这个险也保你在步行时候碰到事故的情况。在有些州，这项保险是强制购买的。这个险也是包括两部分：每人赔付额，单次事故赔付最高金额。\n" +
                "\n" +
                "而买保险的时候，经常有半保（半险）、全保（全险）的说法，它们的区别是：Coverage只买1+2Liability基础保险的，就是半保。一般贷款买车的，都要求买全保。\n" +
                "\n" +
                "另外，保险公司在卖车险的时候，还会给你推荐一些附加险，比如Emergency Road Service(道路紧急救援)，Rental Reimbursement(租车补偿)等等。\n" +
                "\n" +
                "这些就需要你自己斟酌是否购买，购买哪种，因为这些附加险虽然乍一看很便宜，不过细节还是比较多的，需要自己多比较，比如道路紧急救援，一个月从几块钱到几十块钱的都有，区别有拖车的距离之类的。\n" +
                "\n" +
                "7、路上救援／ Emergency Road Service\n" +
                "\n" +
                "这项车险条款赔付的是，在行驶途中，车坏了，需要找人维修、拖车，或是把自己所在车外，需要叫人来开锁等，所产生的费用。\n" +
                "\n" +
                "8、租车补偿／ Rental reimbursement Coverage\n" +
                "\n" +
                "这项车险条款赔付的是，你自己的车辆因事故导致维修不能使用时，租车所产生的费用。\n" +
                "\n" +
                "9、赔玻璃 ／ Full Safety Glass\n" +
                "\n" +
                "这种特色保险，可以算概率，买保险合算，还是自己修合算。");
        return result;
    }
}
