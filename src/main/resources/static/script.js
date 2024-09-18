function renderData(data){
    const dataContainer = document.getElementById('dataContainer');

    //데이터가 있는지 확인
    if(data && data.length>0){
        data.forEach(item=>{
            //데이터를 화면에 추가
            const dataItem = document.createElement('div');
            dataItem.textContent = `${item.id}:${item.name},${item.type},${item.price}`;
            dataContainer.appendChild(dataItem);
        });
    } else {
        dataContainer.textContent = 'No data available';
    }
}

fetch('http://localhost:8080/api/items')
    .then(response=>response.json())
    .then(data => renderData(data))
    .catch(error=>{
        console.error('Error fetching data: ',error);
        const dataContainer = document.getElementById('dataContainer');
        dataContainer.textContent = 'Error fetching data';
    })