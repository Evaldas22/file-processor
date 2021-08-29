import _ from 'lodash';

function FileProcessingResult({ isFetching, aToG, hToN, oToU, vToZ }) {
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
	
	const BACK_END_URL = "http://localhost:8080" // this should be a env variable

	return (
		<div className="container">
			<div className="row">
				<div className="wordColumn col">
					Words from A to G 
					{!_.isEmpty(aToG) &&
						<a href={BACK_END_URL+"/download?filename=" + aToG.filename} download><i class="fa fa-download"></i></a>
					}<br/>
					{
						!_.isEmpty(aToG) ?
							<ul>
								{aToG.words.map((wordPair, i) => <li key={i}>{wordPair}</li>)}
							</ul> : '-'
					}
				</div>
				<div className="wordColumn col">
					Words from H to T
					{!_.isEmpty(hToN) &&
						<a href={BACK_END_URL+"/download?filename=" + hToN.filename} download><i class="fa fa-download"></i></a>
					}<br/>
					{
						!_.isEmpty(hToN) ?
							<ul>
								{hToN.words.map((wordPair, i) => <li key={i}>{wordPair}</li>)}
							</ul> : '-'
					}
				</div>
				<div className="wordColumn col">
					Words from O to U
					{!_.isEmpty(oToU) &&
						<a href={BACK_END_URL+"/download?filename=" + oToU.filename} download><i class="fa fa-download"></i></a>
					}<br/>
					{
						!_.isEmpty(oToU) ?
							<ul>
								{oToU.words.map((wordPair, i) => <li key={i}>{wordPair}</li>)}
							</ul> : '-'
					}
				</div>
				<div className="wordColumn col"	>
					Words from V to Z
					{!_.isEmpty(vToZ) &&
						<a href={BACK_END_URL+"/download?filename=" + vToZ.filename} download><i class="fa fa-download"></i></a>
					}<br/>
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
