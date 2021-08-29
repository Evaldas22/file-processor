const processFilesRequest = (data) => {
	fetch("/process-files", {
		method: "POST",
		data,
		// headers: {
		// 	"Content-Type": "application/json",
		// },
	}).then((response) => response.json());
};

export default processFilesRequest;
