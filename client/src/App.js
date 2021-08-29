import { useState } from "react";
import "./App.css";
import FileProcessingResult from "./components/FileProcessingResult";
import FileUpload from "./components/FileUpload";

function App() {
	const individualFileSize = "16MB";
	const combinedFileSize = "300MB";

	const [isFetching, setIsFetching] = useState(false);
	const [files, setFiles] = useState([]);
	const [aToG, setAToG] = useState({});
	const [hToN, setHToN] = useState({});
	const [oToU, setOToU] = useState({});
	const [vToZ, setVToZ] = useState({});

	const selectFiles = (event) => {
		setFiles(event.target.files);
	};

	const processFiles = async (event) => {
		event.preventDefault();
		setIsFetching(true);

		const formData = createFormData();

		fetch("/process-files", {
			method: "POST",
			body: formData,
		})
			.then((response) => {
				if (response.ok) {
					return response.json();
				} else {
					return response.text().then((text) => {
						throw new Error(text);
					});
				}
			})
			.then((json) => {
				setAToG(json.wordFrequenciesFromAtoG);
				setHToN(json.wordFrequenciesFromHtoN);
				setOToU(json.wordFrequenciesFromOtoU);
				setVToZ(json.wordFrequenciesFromVtoZ);
				setIsFetching(false);
			})
			.catch((err) => {
				setAToG({});
				setHToN({});
				setOToU({});
				setVToZ({});
				setIsFetching(false);
			});
	};

	const createFormData = () => {
		const formdata = new FormData();

		for (let i = 0; i < files.length; i++) {
			formdata.append("files", files[i]);
		}

		return formdata;
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
				<section className='mt-5'>
					<FileUpload
						files={files}
						selectFiles={selectFiles}
						processFiles={processFiles}
						isFetching={isFetching}
					/>
				</section>
				<section className='mt-5'>
					<FileProcessingResult
						isFetching={isFetching}
						aToG={aToG}
						hToN={hToN}
						oToU={oToU}
						vToZ={vToZ}
					/>
				</section>
			</main>
		</div>
	);
}

export default App;
