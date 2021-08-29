import _ from 'lodash';

function FileProcessingResult({ isFetching, aToG, hToN, oToU, vToZ }) {
	console.log('====',isFetching, aToG, hToN, oToU, vToZ);
	if(isFetching) {
		return (
			<div
				className='spinner-border'
				style={{ width: "3rem", height: "3rem" }}
				role='status'
			>
				<span className='sr-only'></span>
			</div>
		)
	}
	
	return (
		<div className="container">
			<div className="row">
				<div className="wordColumn col">
					Words from A to G<br/>
					{
						!_.isEmpty(aToG) ?
							<ul>
								{aToG.words.map((wordPair, i) => <li key={i}>{wordPair}</li>)}
							</ul> : '-'
					}
				</div>
				<div className="wordColumn col">
					Words from H to T<br/>
					{
						!_.isEmpty(hToN) ?
							<ul>
								{hToN.words.map((wordPair, i) => <li key={i}>{wordPair}</li>)}
							</ul> : '-'
					}
				</div>
				<div className="wordColumn col">
					Words from O to U<br/>
					{
						!_.isEmpty(oToU) ?
							<ul>
								{oToU.words.map((wordPair, i) => <li key={i}>{wordPair}</li>)}
							</ul> : '-'
					}
				</div>
				<div className="wordColumn col"	>
					Words from V to Z<br/>
					{
						!_.isEmpty(vToZ) ?
							<ul>
								{vToZ.words.map((wordPair, i) => <li key={i}>{wordPair}</li>)}
							</ul> : '-'
					}
				</div>
			</div>
		</div>
	)
}

export default FileProcessingResult;
