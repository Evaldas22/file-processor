function FileUpload({ files, selectFiles, processFiles, isFetching }) {
	const areFilesAdded = () => {
		return files && files.length > 0;
	};

	return (
		<div>
			<h3>Upload files</h3>
			<form onSubmit={processFiles}>
				<input
					type='file'
					id='file'
					multiple
					name='file'
					onChange={selectFiles}
					disabled={isFetching}
				/>
				<button
					type='submit'
					className='btn btn-success'
					disabled={!areFilesAdded() || isFetching}
				>
					Process Files
				</button>
			</form>
		</div>
	);
}

export default FileUpload;
