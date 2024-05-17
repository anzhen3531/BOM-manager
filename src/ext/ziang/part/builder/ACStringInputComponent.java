//package ext.ziang.part.builder;
//
//import java.util.ArrayList;
//
//import com.ptc.core.components.rendering.RenderingException;
//import com.ptc.core.components.rendering.guicomponents.ComboBox;
//import com.ptc.core.components.rendering.guicomponents.StringInputComponent;
//
//public class ACStringInputComponent extends StringInputComponent {
//
//	public ACStringInputComponent() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public ACStringInputComponent(String arg0, ArrayList<String> arg1, ArrayList<String> arg2,
//			boolean arg3) {
//		super(arg0, arg1, arg2, arg3);
//		// TODO Auto-generated constructor stub
//	}
//
//	public ACStringInputComponent(String arg0, ArrayList<String> arg1, ArrayList<String> arg2) {
//		super(arg0, arg1, arg2);
//		// TODO Auto-generated constructor stub
//	}
//
//	public ACStringInputComponent(String arg0, int arg1, boolean arg2) {
//		super(arg0, arg1, arg2);
//		// TODO Auto-generated constructor stub
//	}
//
//	public ACStringInputComponent(String arg0, int arg1, int arg2) {
//		super(arg0, arg1, arg2);
//		// TODO Auto-generated constructor stub
//	}
//
//	public ACStringInputComponent(String arg0, int arg1) {
//		super(arg0, arg1);
//		// TODO Auto-generated constructor stub
//	}
//
//	public ACStringInputComponent(String arg0) {
//		super(arg0);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public void createUI() throws RenderingException {
//		// TODO Auto-generated method stub
//		super.createUI();
//		ComboBox cb = (ComboBox) super.textUI;
//		if ("FUnitGroupID".equalsIgnoreCase(getId())) {
//			cb.addJsAction("onchange", "changeUnitGroupDropdownList(this)");
//		}
//		if ("FIsBackFlush".equalsIgnoreCase(getId())) {
//			cb.addJsAction("onchange", "changeBackFlushDropdownList(this)");
//		}
//		if ("FErpClsID".equalsIgnoreCase(getId())) {
//			cb.addJsAction("onchange", "changeErpClsDropdownList(this)");
//		}
//
//	}
//
//}
