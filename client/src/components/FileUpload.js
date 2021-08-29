function FileUpload(props) {
	return (
		<div>
			<h3>Upload files</h3>
			<form onSubmit={props.processFiles}>
				<input
					type='file'
					id='file'
					multiple
					name='file'
					onChange={props.selectFiles}
				/>
				<button
					type='submit'
					className='btn btn-info'
					disabled={!props.files || props.files.length === 0}
				>
					Process Files
				</button>
			</form>
		</div>
	);
}

export default FileUpload;
