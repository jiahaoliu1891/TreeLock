/*
 * This is where you will be implementing your TreeLock
 * 
 */
import java.util.ArrayList;
import java.util.List;

public class TreeLock {
	int nThreads;
	public PetersonNode root;
	public List<PetersonNode> leaves;


	public TreeLock(int _numThr) {
		// validate input thread number (must be power of 2)
		// if ((_numThr & -_numThr) != _numThr) {
		// 	throw new IllegalArgumentException("Thread count must be power of 2.");
		// }

		nThreads = _numThr;
		root = new PetersonNode(null, nThreads);
		List<PetersonNode> initList = new ArrayList<>();
		initList.add(root);
		leaves = initPetersonNodes(initList, 0);
	}

	private List<PetersonNode> initPetersonNodes(List<PetersonNode> nodes, int level) {
		if (nodes.size() == nThreads / 2)
			return nodes;

		List<PetersonNode> curNodes = new ArrayList<>();

		// int maxThreadID = level;

		for (PetersonNode node : nodes) {
			node.left = new PetersonNode(node, nThreads);
			node.right = new PetersonNode(node, nThreads);
			curNodes.add(node.left);
			curNodes.add(node.right);
		}
		// Recurse, passing our current leaves back to the function
		return initPetersonNodes(curNodes, level + 1);
	}

	private PetersonNode getLockByID(int threadID) {
		return leaves.get(threadID / 2);
	}

	public void lock() {
		int threadID = ThreadID.get();
		// if (threadID == 0) {
		// 	System.out.println(threadID);
		// }
		PetersonNode curNode = getLockByID(threadID);
		while(curNode != null) {
			curNode.lock(threadID);
			curNode = curNode.parent;
		}
	}

	public void unlock() {
		int threadID = ThreadID.get();
		PetersonNode curNode = getLockByID(threadID);

		while (curNode != null) {
			curNode.unlock(threadID);
			curNode = curNode.parent;
		}
	}
}


