import './App.css';
import 'bootstrap/dist/css/bootstrap.css'
import React, {useState} from 'react';

function Product(props) {
    const productName = props.productName;
    const category = props.category;
    const price = props.price;

    return (
        <React.Fragment>
            <div className="col-2">
                <img className="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/>
            </div>
            <div className="col">
                <div className="row text-muted">{category}</div>
                <div className="row">{productName}</div>
            </div>
            <div className="col text-center price">{price}원</div>
            <div className="col text-end action">
                <button className="btn btn-small btn-outline-dark">추가</button>
            </div>
        </React.Fragment>
    )
}

function ProductList({products}) {
    return (
        <React.Fragment>
            <h5 className="flex-grow-0"><b>상품 목록</b></h5>
            <ul className="list-group products">
                {products.map(product =>
                    <li key={product.id} className="list-group-item d-flex mt-3">
                        <Product productName={product.productName} category={product.category} price={product.price}/>
                    </li>
                )}
            </ul>
        </React.Fragment>
    )
}

function SummaryItem({productName, count}) {
    return (
        <div className="row">
            <h6 className="p-0">{productName} <span className="badge bg-dark text-">{count}개</span></h6>
        </div>
    )
}

function Summary({items = []}) {
    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0)
    return (
        <React.Fragment>
            <div>
                <h5 className="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr/>
            {items.map(item => <SummaryItem key={item.id} count={item.count} productName={item.productName}/>)}
            <form>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input type="email" className="form-control mb-1" id="email"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">주소</label>
                    <input type="text" className="form-control mb-1" id="address"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="postcode" className="form-label">우편번호</label>
                    <input type="text" className="form-control" id="postcode"/>
                </div>
                <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
            </form>
            <div className="row pt-2 pb-2 border-top">
                <h5 className="col">총금액</h5>
                <h5 className="col text-end">{totalPrice}</h5>
            </div>
            <button className="btn btn-dark col-12">결제하기</button>
        </React.Fragment>
    )
}

function App() {
    const [products, setProducts] = useState([
        {id: 'uuid-1', productName: '콜롬비아 커피1', category: "커피콩", price: 3000},
        {id: 'uuid-2', productName: '콜롬비아 커피2', category: "커피콩", price: 7000},
        {id: 'uuid-3', productName: '콜롬비아 커피3', category: "커피콩", price: 4000}
    ]);

    const [items, setItems] = useState([

    ]);


    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Grids & Circle</h1>
            </div>
            <div className="card">
                <div className="row">
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <ProductList products={products}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <Summary items={items}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
