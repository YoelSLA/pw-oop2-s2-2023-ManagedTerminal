package search.binaryOperator;

import search.Search;

public abstract class BinaryOperator implements Search {

	private Search leftClause;
	private Search rightClause;

	public BinaryOperator(Search leftClause, Search rightClause) {
		this.leftClause = leftClause;
		this.rightClause = rightClause;
	}

	public Search getLeftClause() {
		return leftClause;
	}

	public Search getRightClause() {
		return rightClause;
	}

	public void setLeftClause(Search leftClause) {
		this.leftClause = leftClause;
	}

	public void setRightClause(Search rightClause) {
		this.rightClause = rightClause;
	}

}
