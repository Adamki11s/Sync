package couk.Adamki11s.IO.Branches;

import java.util.ArrayList;

public class Branch extends BranchData {
	
	public Branch(String key, Object value) {
		super(key, value);
	}
	private ArrayList<SubBranch> subBranches = new ArrayList<SubBranch>();
	private ArrayList<BranchData> data = new ArrayList<BranchData>();

}
