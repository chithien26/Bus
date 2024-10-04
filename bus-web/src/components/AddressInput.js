import React, { useEffect, useState } from 'react';
import axios from 'axios';

const AddressInput = ({ onSelect }) => {
    const [address, setAddress] = useState('');
    const [suggestions, setSuggestions] = useState([]);
    const [loading, setLoading] = useState(false);

    const handleInputChange = async (event) => {
        const value = event.target.value;
        setAddress(value);

        if (value) {
            setLoading(true);
            try {
                const response = await axios.get(`https://maps.googleapis.com/maps/api/place/autocomplete/json`, {
                    params: {
                        input: value,
                        key: 'YOUR_API_KEY', // Thay YOUR_API_KEY bằng API key của bạn
                        language: 'vi', // Ngôn ngữ Việt Nam
                    }
                });
                setSuggestions(response.data.predictions);
            } catch (error) {
                console.error('Error fetching address suggestions:', error);
            } finally {
                setLoading(false);
            }
        } else {
            setSuggestions([]);
        }
    };

    const handleSuggestionClick = (suggestion) => {
        setAddress(suggestion.description);
        setSuggestions([]);
        onSelect(suggestion); // Truyền gợi ý đã chọn về component cha nếu cần
    };

    return (
        <div style={{ position: 'relative', width: '100%' }}>
            <input
                type="text"
                value={address}
                onChange={handleInputChange}
                placeholder="Nhập địa chỉ"
                style={{ width: '100%', padding: '8px', marginBottom: '8px' }}
            />
            {loading && <p>Loading suggestions...</p>}
            {suggestions.length > 0 && (
                <ul style={{ 
                    listStyleType: 'none', 
                    padding: 0, 
                    position: 'absolute', 
                    backgroundColor: 'white', 
                    border: '1px solid #ddd', 
                    width: '100%', 
                    zIndex: 1 
                }}>
                    {suggestions.map((suggestion) => (
                        <li
                            key={suggestion.place_id}
                            onClick={() => handleSuggestionClick(suggestion)}
                            style={{ 
                                cursor: 'pointer', 
                                padding: '8px', 
                                borderBottom: '1px solid #ddd' 
                            }}
                        >
                            {suggestion.description}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default AddressInput;
