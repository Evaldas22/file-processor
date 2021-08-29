import { useState } from "react";
import "./App.css";
import FileUpload from "./components/FileUpload";
import processFilesRequest from "./services/processFilesService";

function App() {
	const individualFileSize = "16MB";
	const combinedFileSize = "300MB";

	const [files, setFiles] = useState([]);

	const selectFiles = (event) => {
		setFiles(event.target.files);
	};

	const processFiles = async (event) => {
		event.preventDefault();
		var formData = new FormData();
		for (let i = 0; i < files.length; i++) {
			formData.append(`files[${i}]`, files[i]);
		}
		console.log(formData);
		const data = await processFilesRequest(formData);
		console.log(data);
	};

	return (
		<div className='App'>
			<header>
				<h1>File processor</h1>
			</header>
			<main>
				<section>
					<p>
						This is a very awesome app that takes in any amount of text files
						(each file up to {individualFileSize} and {combinedFileSize}{" "}
						combined) and calculates a frequency of words in them.
						<br />
						The frequencies are divided into four lists, depending on the first
						letter of the word. These are A-G, H-N, O-U and V-Z.
					</p>
				</section>
				<section className='uploadFilesSection'>
					<FileUpload
						files={files}
						selectFiles={selectFiles}
						processFiles={processFiles}
					/>
				</section>
			</main>
		</div>
	);
}

export default App;
