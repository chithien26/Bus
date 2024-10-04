import React from 'react';
import AddressInput from './AddressInput'; // Đảm bảo đường dẫn đúng

const RouteSearchPage = () => {
    const handleAddressSelect = (suggestion) => {
        console.log('Selected address:', suggestion);
        // Xử lý gợi ý đã chọn ở đây
    };

    return (
        <div>
            <h2>Tìm kiếm tuyến đường</h2>
            <AddressInput onSelect={handleAddressSelect} />
            {/* Thêm các component khác ở đây nếu cần */}
        </div>
    );
};

export default RouteSearchPage;
